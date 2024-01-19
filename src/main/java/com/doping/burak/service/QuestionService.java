package com.doping.burak.service;

import com.doping.burak.model.Question;

import java.util.List;

public interface QuestionService {

    Question getQuestionById(Long id);

    List<Question> getAllQuestions();

    Question createQuestion(Question question);

    boolean deleteQuestion(Long id);

    Question updateQuestion(Long id, Question question);

    List<Question> getQuestionsByExamId(Long examId);
}
