package com.doping.burak.controller;

import com.doping.burak.dto.ExamReport;
import com.doping.burak.model.StudentAnswer;
import com.doping.burak.service.StudentAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/studentAnswers")
public class StudentAnswerController {

    private final StudentAnswerService studentAnswerService;

    @GetMapping("/")
    public ResponseEntity<List<StudentAnswer>> getAllStudentAnswers() {
        List<StudentAnswer> studentAnswers = studentAnswerService.getAllStudentAnswers();
        return new ResponseEntity<>(studentAnswers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentAnswer> getStudentAnswerById(@PathVariable Long id) {
        StudentAnswer studentAnswer = studentAnswerService.getStudentAnswerById(id);
        if (studentAnswer != null) {
            return new ResponseEntity<>(studentAnswer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentAnswer(@PathVariable Long id, @RequestBody StudentAnswer studentAnswer) {
        try {
            StudentAnswer updatedStudentAnswer = studentAnswerService.updateStudentAnswer(id, studentAnswer);
            if (updatedStudentAnswer != null) {
                return new ResponseEntity<>(updatedStudentAnswer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentAnswer(@PathVariable Long id) {
        boolean deleted = studentAnswerService.deleteStudentAnswer(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> submitAnswer(@RequestBody StudentAnswer studentAnswer) {
        try {
            StudentAnswer createdAnswer = studentAnswerService.submitAnswer(studentAnswer);
            if (createdAnswer != null) {
                return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/exam/{examId}/student/{studentId}/report")
    public ResponseEntity<?> getExamReport(@PathVariable Long examId, @PathVariable Long studentId) {
        try {
            ExamReport examReport = studentAnswerService.calculateExamReport(examId, studentId);
            if (examReport != null) {
                return new ResponseEntity<>(examReport, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}