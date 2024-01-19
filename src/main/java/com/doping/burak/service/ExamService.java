package com.doping.burak.service;

import com.doping.burak.model.Exam;

import java.util.List;

public interface ExamService {

    List<Exam> getAllExams();

    Exam getExamById(Long id);

    Exam createExam(Exam exam);

    boolean deleteExam(Long id);

    Exam updateExam(Long id, Exam exam);
}
