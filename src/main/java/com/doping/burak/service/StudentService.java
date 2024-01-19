package com.doping.burak.service;

import com.doping.burak.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    boolean deleteStudent(Long id);
    Optional<List<Student>> getStudentsByExamId(Long examId);
    Optional<List<Student>> getStudentsByExamName(String examName);
}
