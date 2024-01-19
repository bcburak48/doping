package com.doping.burak.controller;

import com.doping.burak.model.StudentExam;
import com.doping.burak.service.StudentExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentExamControllerTest {

    @Mock
    private StudentExamService studentExamService;

    @InjectMocks
    private StudentExamController studentExamController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentExamController).build();
    }

    @Test
    public void testGetAllStudentExams() throws Exception {
        List<StudentExam> studentExams = new ArrayList<>();
        studentExams.add(new StudentExam());
        studentExams.add(new StudentExam());

        when(studentExamService.getAllStudentExams()).thenReturn(studentExams);

        mockMvc.perform(get("/api/studentExams/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(studentExams.size()));
    }

    @Test
    public void testGetStudentExamById() throws Exception {
        Long studentExamId = 1L;
        StudentExam studentExam = new StudentExam();
        studentExam.setId(studentExamId);

        when(studentExamService.getStudentExamById(studentExamId)).thenReturn(studentExam);

        mockMvc.perform(get("/api/studentExams/{id}", studentExamId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentExam.getId()));
    }

    @Test
    public void testGetStudentExamByIdNotFound() throws Exception {
        Long studentExamId = 1L;

        when(studentExamService.getStudentExamById(studentExamId)).thenReturn(null);

        mockMvc.perform(get("/api/studentExams/{id}", studentExamId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStudentExam() throws Exception {
        StudentExam studentExam = new StudentExam();
        Long studentExamId = 1L;
        studentExam.setId(studentExamId);

        when(studentExamService.createStudentExam(any(StudentExam.class))).thenReturn(studentExam);

        mockMvc.perform(post("/api/studentExams/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(studentExamId));
    }

    @Test
    public void testUpdateStudentExam() throws Exception {
        Long studentExamId = 1L;
        StudentExam existingStudentExam = new StudentExam();
        existingStudentExam.setId(studentExamId);

        when(studentExamService.updateStudentExam(eq(studentExamId), any(StudentExam.class))).thenReturn(existingStudentExam);

        mockMvc.perform(put("/api/studentExams/{id}", studentExamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentExamId));
    }

    @Test
    public void testUpdateStudentExamNotFound() throws Exception {
        Long studentExamId = 1L;

        when(studentExamService.updateStudentExam(eq(studentExamId), any(StudentExam.class))).thenReturn(null);

        mockMvc.perform(put("/api/studentExams/{id}", studentExamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteStudentExam() throws Exception {
        Long studentExamId = 1L;

        when(studentExamService.deleteStudentExam(studentExamId)).thenReturn(true);

        mockMvc.perform(delete("/api/studentExams/{id}", studentExamId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteStudentExamNotFound() throws Exception {
        Long studentExamId = 1L;

        when(studentExamService.deleteStudentExam(studentExamId)).thenReturn(false);

        mockMvc.perform(delete("/api/studentExams/{id}", studentExamId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testJoinExam() throws Exception {
        Long studentId = 1L;
        Long examId = 2L;

        mockMvc.perform(post("/api/studentExams/students/{studentId}/exams/{examId}/join", studentId, examId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testJoinExamStudentNotFound() throws Exception {
        Long studentId = 1L;
        Long examId = 2L;

        doThrow(new IllegalArgumentException("Student not found")).when(studentExamService).joinExam(eq(studentId), eq(examId));

        try {
            mockMvc.perform(post("/api/studentExams/students/{studentId}/exams/{examId}/join", studentId, examId));
        } catch (NestedServletException ex) {
            assert(ex.getCause()).getMessage().contains("Student not found");
        }
    }

    @Test
    public void testJoinExamExamNotFound() throws Exception {
        Long studentId = 1L;
        Long examId = 2L;

        doThrow(new IllegalArgumentException("Exam not found")).when(studentExamService).joinExam(eq(studentId), eq(examId));

        try {
            mockMvc.perform(post("/api/studentExams/students/{studentId}/exams/{examId}/join", studentId, examId));
        } catch (NestedServletException ex) {
            assert(ex.getCause()).getMessage().contains("Exam not found");
        }
    }
}
