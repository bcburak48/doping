package com.doping.burak.service.impl;

import com.doping.burak.dto.ExamReport;
import com.doping.burak.model.StudentAnswer;
import com.doping.burak.repository.StudentAnswerRepository;
import com.doping.burak.service.StudentAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentAnswerServiceImpl implements StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;

    @Override
    public List<StudentAnswer> getAllStudentAnswers() {
        return studentAnswerRepository.findAll();
    }

    @Override
    public StudentAnswer getStudentAnswerById(Long id) {
        Optional<StudentAnswer> studentAnswer = studentAnswerRepository.findById(id);
        return studentAnswer.orElse(null);
    }

    @Override
    public StudentAnswer updateStudentAnswer(Long id, StudentAnswer studentAnswer) {
        Optional<StudentAnswer> existingStudentAnswer = studentAnswerRepository.findById(id);
        if (existingStudentAnswer.isPresent()) {
            studentAnswer.setId(id);
            return studentAnswerRepository.save(studentAnswer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Answer not found with ID: " + id);
        }
    }

    @Override
    public boolean deleteStudentAnswer(Long id) {
        if (studentAnswerRepository.existsById(id)) {
            studentAnswerRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Answer not found with ID: " + id);
        }
    }

    @Override
    public ExamReport calculateExamReport(Long examId, Long studentId) {
        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentExam_Exam_IdAndStudentExam_Student_Id(examId, studentId);
        int totalQuestions = studentAnswers.size();
        int correctAnswers = 0;

        for (StudentAnswer studentAnswer : studentAnswers) {
            if (studentAnswer.getAnswer().equals(studentAnswer.getQuestion().getQuestionAnswer())) {
                correctAnswers++;
            }
        }

        int incorrectAnswers = totalQuestions - correctAnswers;

        return new ExamReport(totalQuestions, correctAnswers, incorrectAnswers);
    }

    @Override
    public StudentAnswer submitAnswer(StudentAnswer studentAnswer) {
        Long studentId = studentAnswer.getStudentExam().getStudent().getId();
        Long questionId = studentAnswer.getQuestion().getId();

        if (!hasStudentAnsweredQuestion(studentId, questionId)) {
            return studentAnswerRepository.save(studentAnswer);
        } else {
            throw new IllegalArgumentException("Student has already answered this question.");
        }
    }

    public boolean hasStudentAnsweredQuestion(Long studentId, Long questionId) {
        return studentAnswerRepository.existsByStudent_IdAndQuestion_Id(studentId, questionId);
    }

}
