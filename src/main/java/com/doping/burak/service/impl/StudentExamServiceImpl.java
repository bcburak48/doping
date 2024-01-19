package com.doping.burak.service.impl;

import com.doping.burak.model.Exam;
import com.doping.burak.model.Student;
import com.doping.burak.model.StudentExam;
import com.doping.burak.repository.ExamRepository;
import com.doping.burak.repository.StudentExamRepository;
import com.doping.burak.repository.StudentRepository;
import com.doping.burak.service.StudentExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentExamServiceImpl implements StudentExamService {

    private final StudentExamRepository studentExamRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    @Override
    @Cacheable("studentExams")
    public List<StudentExam> getAllStudentExams() {
        return studentExamRepository.findAll();
    }

    @Override
    @Cacheable("studentExamById")
    public StudentExam getStudentExamById(Long id) {
        Optional<StudentExam> studentExam = studentExamRepository.findById(id);
        return studentExam.orElse(null);
    }

    @Override
    public List<StudentExam> getActiveStudentExams(Student student) {
        return studentExamRepository.findByStudentAndIsActive(student, true);
    }

    @Override
    public StudentExam createStudentExam(StudentExam studentExam) {
        return studentExamRepository.save(studentExam);
    }

    @Override
    public StudentExam updateStudentExam(Long id, StudentExam studentExam) {
        Optional<StudentExam> existingStudentExam = studentExamRepository.findById(id);
        if (existingStudentExam.isPresent()) {
            StudentExam updatedStudentExam = existingStudentExam.get();
            updatedStudentExam.setStudent(studentExam.getStudent());
            updatedStudentExam.setExam(studentExam.getExam());
            return studentExamRepository.save(updatedStudentExam);
        } else {
            return null;
        }
    }


    @Override
    public boolean deleteStudentExam(Long id) {
        Optional<StudentExam> existingStudentExam = studentExamRepository.findById(id);
        if (existingStudentExam.isPresent()) {
            studentExamRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void joinExam(Long studentId, Long examId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Exam exam = examRepository.findById(examId).orElse(null);

        if (student != null && exam != null) {
            StudentExam studentExam = new StudentExam();
            studentExam.setStudent(student);
            studentExam.setExam(exam);
            studentExamRepository.save(studentExam);
        }
    }
}
