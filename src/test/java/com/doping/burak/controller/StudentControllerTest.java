package com.doping.burak.controller;

import com.doping.burak.model.Student;
import com.doping.burak.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        studentService = Mockito.mock(StudentService.class);
        studentController = new StudentController(studentService);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());

        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<Student>> response = studentController.getAllStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
    }

    @Test
    public void testGetStudentById() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        when(studentService.getStudentById(studentId)).thenReturn(student);

        ResponseEntity<?> response = studentController.getStudentById(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student();
        newStudent.setFirstName("John");
        newStudent.setLastName("Doe");

        when(studentService.createStudent(newStudent)).thenReturn(newStudent);

        ResponseEntity<?> response = studentController.createStudent(newStudent);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newStudent, response.getBody());
    }

    @Test
    public void testUpdateStudent() {
        Long studentId = 1L;
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("John");
        existingStudent.setLastName("Doe");

        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setFirstName("Jane");
        updatedStudent.setLastName("Doe");

        when(studentService.updateStudent(studentId, updatedStudent)).thenReturn(updatedStudent);

        ResponseEntity<?> response = studentController.updateStudent(studentId, updatedStudent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());
    }

    @Test
    public void testDeleteStudent() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);

        when(studentService.deleteStudent(studentId)).thenReturn(true);

        ResponseEntity<?> response = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteNonExistentStudent() {
        Long studentId = 999L;

        when(studentService.deleteStudent(studentId)).thenReturn(false);

        ResponseEntity<?> response = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStudentsByExamId() {
        Long examId = 1L;

        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());

        when(studentService.getStudentsByExamId(examId)).thenReturn(Optional.of(students));

        ResponseEntity<?> response = studentController.getStudentsByExamId(examId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(students, response.getBody());
    }

    @Test
    public void testGetStudentsByExamName() {

        String examName = "Math Exam";

        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());

        when(studentService.getStudentsByExamName(examName)).thenReturn(Optional.of(students));

        ResponseEntity<?> response = studentController.getStudentsByExamName(examName);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(students, response.getBody());
    }
}
