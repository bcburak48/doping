package com.doping.burak.controller;

import com.doping.burak.model.*;
import com.doping.burak.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studentExams")
@RequiredArgsConstructor
public class StudentExamController {

    private final StudentService studentService;
    private final ExamService examService;
    private final StudentExamService studentExamService;
    private final StudentAnswerService studentAnswerService;

    @GetMapping("/")
    public ResponseEntity<List<StudentExam>> getAllStudentExams() {
        List<StudentExam> studentExams = studentExamService.getAllStudentExams();
        return new ResponseEntity<>(studentExams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentExam> getStudentExamById(@PathVariable Long id) {
        StudentExam studentExam = studentExamService.getStudentExamById(id);
        if (studentExam != null) {
            return new ResponseEntity<>(studentExam, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<StudentExam> createStudentExam(@RequestBody StudentExam studentExam) {
        StudentExam createdStudentExam = studentExamService.createStudentExam(studentExam);
        return new ResponseEntity<>(createdStudentExam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentExam(@PathVariable Long id, @RequestBody StudentExam studentExam) {
        try {
            StudentExam updatedStudentExam = studentExamService.updateStudentExam(id, studentExam);
            if (updatedStudentExam != null) {
                return new ResponseEntity<>(updatedStudentExam, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentExam(@PathVariable Long id) {
        boolean deleted = studentExamService.deleteStudentExam(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/students/{studentId}/exams/{examId}/join")
    public ResponseEntity<?> joinExam(@PathVariable Long studentId, @PathVariable Long examId) {
        try {
            Student student = studentService.getStudentById(studentId);
            Exam exam = examService.getExamById(examId);

            if (student != null && exam != null) {
                StudentExam studentExam = new StudentExam();
                studentExam.setStudent(student);
                studentExam.setExam(exam);
                studentExamService.createStudentExam(studentExam);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Student or Exam not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/students/{studentId}/active-exams")
    public ResponseEntity<?> getActiveExams(@PathVariable Long studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            if (student != null) {
                List<StudentExam> studentExams = studentExamService.getActiveStudentExams(student);
                if (!studentExams.isEmpty()) {
                    return new ResponseEntity<>(studentExams, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
