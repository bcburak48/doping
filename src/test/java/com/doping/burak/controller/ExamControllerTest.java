package com.doping.burak.controller;

import com.doping.burak.model.Exam;
import com.doping.burak.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ExamController(examService)).build();
    }

    @Test
    public void testGetAllExams() throws Exception {
        List<Exam> exams = new ArrayList<>();
        exams.add(new Exam());
        exams.add(new Exam());

        when(examService.getAllExams()).thenReturn(exams);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(exams.size()));

        verify(examService, times(1)).getAllExams();
        verifyNoMoreInteractions(examService);
    }

    @Test
    public void testGetExamById() throws Exception {
        Long examId = 1L;
        Exam exam = new Exam();
        exam.setId(examId);

        when(examService.getExamById(examId)).thenReturn(exam);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/{id}", examId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exam.getId()));

        verify(examService, times(1)).getExamById(examId);
        verifyNoMoreInteractions(examService);
    }

    @Test
    public void testCreateExam() throws Exception {
        Exam exam = new Exam();
        exam.setExamName("Sample Exam");

        when(examService.createExam(any(Exam.class))).thenReturn(exam);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/exams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"examName\":\"Sample Exam\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.examName").value(exam.getExamName()));

        verify(examService, times(1)).createExam(any(Exam.class));
        verifyNoMoreInteractions(examService);
    }

    @Test
    public void testUpdateExam() throws Exception {
        Long examId = 1L;
        Exam exam = new Exam();
        exam.setId(examId);
        exam.setExamName("Updated Exam");

        when(examService.updateExam(eq(examId), any(Exam.class))).thenReturn(exam);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/exams/{id}", examId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"examName\":\"Updated Exam\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examName").value(exam.getExamName()));

        verify(examService, times(1)).updateExam(eq(examId), any(Exam.class));
        verifyNoMoreInteractions(examService);
    }

    @Test
    public void testDeleteExam() throws Exception {
        Long examId = 1L;

        when(examService.deleteExam(examId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/exams/{id}", examId))
                .andExpect(status().isNoContent());

        verify(examService, times(1)).deleteExam(examId);
        verifyNoMoreInteractions(examService);
    }
}
