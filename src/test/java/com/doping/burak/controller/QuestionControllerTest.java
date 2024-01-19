package com.doping.burak.controller;

import com.doping.burak.model.Question;
import com.doping.burak.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionControllerTest {

    @InjectMocks
    private QuestionController questionController;

    @Mock
    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "Soru 1", "Cevap 1"));
        questions.add(new Question(2L, "Soru 2", "Cevap 2"));

        when(questionService.getAllQuestions()).thenReturn(questions);

        ResponseEntity<List<Question>> response = questionController.getAllQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testGetQuestionById() {
        Long questionId = 1L;
        Question question = new Question(questionId, "Soru 1", "Cevap 1");

        when(questionService.getQuestionById(questionId)).thenReturn(question);

        ResponseEntity<?> response = questionController.getQuestionById(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(question, response.getBody());
    }

    @Test
    public void testGetQuestionById_NotFound() {
        Long questionId = 1L;
        when(questionService.getQuestionById(questionId)).thenReturn(null);

        ResponseEntity<?> response = questionController.getQuestionById(questionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Question not found", response.getBody());
    }

    @Test
    public void testCreateQuestion() {
        Question questionToCreate = new Question(null, "Yeni Soru", "Yeni Cevap");
        Question createdQuestion = new Question(1L, "Yeni Soru", "Yeni Cevap");

        when(questionService.createQuestion(questionToCreate)).thenReturn(createdQuestion);

        ResponseEntity<?> response = questionController.createQuestion(questionToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdQuestion, response.getBody());
    }

    @Test
    public void testUpdateQuestion() {
        Long questionId = 1L;
        Question updatedQuestion = new Question(questionId, "Güncellenmiş Soru", "Güncellenmiş Cevap");

        when(questionService.updateQuestion(questionId, updatedQuestion)).thenReturn(updatedQuestion);

        ResponseEntity<?> response = questionController.updateQuestion(questionId, updatedQuestion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedQuestion, response.getBody());
    }

    @Test
    public void testDeleteQuestion() {
        Long questionId = 1L;
        when(questionService.deleteQuestion(questionId)).thenReturn(true);

        ResponseEntity<?> response = questionController.deleteQuestion(questionId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetQuestionsByExamId() {
        Long examId = 1L;
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "Soru 1", "Cevap 1"));
        questions.add(new Question(2L, "Soru 2", "Cevap 2"));

        when(questionService.getQuestionsByExamId(examId)).thenReturn(questions);

        ResponseEntity<?> response = questionController.getQuestionsByExamId(examId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, ((List<Question>) response.getBody()).size());
    }
}
