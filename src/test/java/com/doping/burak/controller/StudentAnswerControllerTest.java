package com.doping.burak.controller;

import com.doping.burak.model.StudentAnswer;
import com.doping.burak.service.StudentAnswerService;
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

import static org.mockito.Mockito.when;

public class StudentAnswerControllerTest {

    @InjectMocks
    private StudentAnswerController studentAnswerController;

    @Mock
    private StudentAnswerService studentAnswerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllStudentAnswers() {

        List<StudentAnswer> studentAnswers = new ArrayList<>();

        when(studentAnswerService.getAllStudentAnswers()).thenReturn(studentAnswers);

        ResponseEntity<List<StudentAnswer>> response = studentAnswerController.getAllStudentAnswers();

        assert(response.getStatusCode() == HttpStatus.OK);

        assert(Objects.requireNonNull(response.getBody()).size() == studentAnswers.size());
    }

    @Test
    public void testGetStudentAnswerById() {
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);

        when(studentAnswerService.getStudentAnswerById(1L)).thenReturn(studentAnswer);

        ResponseEntity<StudentAnswer> response = studentAnswerController.getStudentAnswerById(1L);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.requireNonNull(response.getBody()).getId() == 1L);
    }

    @Test
    public void testGetStudentAnswerById_NotFound() {
        when(studentAnswerService.getStudentAnswerById(1L)).thenReturn(null);

        ResponseEntity<StudentAnswer> response = studentAnswerController.getStudentAnswerById(1L);

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateStudentAnswer() {
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);

        when(studentAnswerService.updateStudentAnswer(1L, studentAnswer)).thenReturn(studentAnswer);

        ResponseEntity<?> response = studentAnswerController.updateStudentAnswer(1L, studentAnswer);

        assert(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void testUpdateStudentAnswer_NotFound() {
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);

        when(studentAnswerService.updateStudentAnswer(1L, studentAnswer)).thenReturn(null);

        ResponseEntity<?> response = studentAnswerController.updateStudentAnswer(1L, studentAnswer);

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteStudentAnswer() {
        when(studentAnswerService.deleteStudentAnswer(1L)).thenReturn(true);

        ResponseEntity<Void> response = studentAnswerController.deleteStudentAnswer(1L);

        assert(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteStudentAnswer_NotFound() {
        when(studentAnswerService.deleteStudentAnswer(1L)).thenReturn(false);

        ResponseEntity<Void> response = studentAnswerController.deleteStudentAnswer(1L);

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}
