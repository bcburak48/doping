package com.doping.burak.service;

import com.doping.burak.dto.ExamReport;
import com.doping.burak.model.StudentAnswer;
import com.doping.burak.model.StudentExam;

import java.util.List;

public interface StudentAnswerService {

    List<StudentAnswer> getAllStudentAnswers();

    StudentAnswer getStudentAnswerById(Long id);

    StudentAnswer updateStudentAnswer(Long id, StudentAnswer studentAnswer);

    boolean deleteStudentAnswer(Long id);

    ExamReport calculateExamReport(Long examId, Long studentId);

    StudentAnswer submitAnswer(StudentAnswer studentAnswer);
}
