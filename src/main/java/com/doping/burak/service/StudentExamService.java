package com.doping.burak.service;

import com.doping.burak.model.Student;
import com.doping.burak.model.StudentExam;

import java.util.List;

public interface StudentExamService {
    StudentExam createStudentExam(StudentExam studentExam);

    boolean deleteStudentExam(Long id);

    List<StudentExam> getAllStudentExams();

    StudentExam updateStudentExam(Long id, StudentExam studentExam);

    StudentExam getStudentExamById(Long id);

    List<StudentExam> getActiveStudentExams(Student student);

    void joinExam(Long studentId, Long examId);

}
