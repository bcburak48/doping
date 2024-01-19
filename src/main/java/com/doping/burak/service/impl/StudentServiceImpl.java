package com.doping.burak.service.impl;

import com.doping.burak.model.Student;
import com.doping.burak.repository.StudentRepository;
import com.doping.burak.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Cacheable("students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @CachePut(value = "students", key = "#result.id")
    public Student createStudent(@Valid Student student) {
        if (student.getId() != null) {
            throw new IllegalArgumentException("Student ID should not be provided for creation.");
        }
        return studentRepository.save(student);
    }

    @CachePut(value = "students", key = "#id")
    public Student updateStudent(Long id, @Valid Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student not found with ID: " + id);
        }
    }

    @Override
    @CacheEvict(value = "students", key = "#id")
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with ID: " + id);
        }
    }

    @Override
    @Cacheable("studentsByExamId")
    public Optional<List<Student>> getStudentsByExamId(@NotNull Long examId) {
        return studentRepository.findStudentsByStudentExamsExamId(examId);
    }

    @Override
    @Cacheable("studentsByExamName")
    public Optional<List<Student>> getStudentsByExamName(String examName) {
        return studentRepository.findStudentsByStudentExamsExamExamName(examName);
    }
}
