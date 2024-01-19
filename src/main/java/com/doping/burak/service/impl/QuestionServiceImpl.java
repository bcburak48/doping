package com.doping.burak.service.impl;

import com.doping.burak.model.Question;
import com.doping.burak.repository.QuestionRepository;
import com.doping.burak.service.QuestionService;
import jakarta.validation.Valid;
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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Cacheable(value = "questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Cacheable(value = "questions", key = "#id")
    public Question getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.orElse(null);
    }

    @Cacheable(value = "questionsByExam", key = "#exam.id")
    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    @CachePut(value = "questions", key = "#result.id")
    public Question createQuestion(@Valid Question question) {
        if (question.getId() != null) {
            throw new IllegalArgumentException("Question ID should not be provided for creation.");
        }
        return questionRepository.save(question);
    }

    @CachePut(value = "questions", key = "#id")
    public Question updateQuestion(Long id, @Valid Question question) {
        Optional<Question> optionalExistingQuestion = questionRepository.findById(id);
        if (optionalExistingQuestion.isPresent()) {
            Question existingQuestion = optionalExistingQuestion.get();
            existingQuestion.setQuestionText(question.getQuestionText());
            existingQuestion.setQuestionAnswer(question.getQuestionAnswer());
            existingQuestion.setExam(question.getExam());
            return questionRepository.save(existingQuestion);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with ID: " + id);
        }
    }

    @CacheEvict(value = "questions", key = "#id")
    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found with ID: " + id);
        }
    }
}

