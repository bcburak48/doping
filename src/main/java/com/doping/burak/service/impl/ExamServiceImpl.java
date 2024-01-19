package com.doping.burak.service.impl;

import com.doping.burak.model.Exam;
import com.doping.burak.repository.ExamRepository;
import com.doping.burak.service.ExamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    @Cacheable("exams")
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        Optional<Exam> optionalExam = examRepository.findById(id);
        return optionalExam.orElse(null);
    }

    @CachePut(value = "exams", key = "#exam.id")
    public Exam createExam(@Valid @NotNull Exam exam) {
        if (exam.getId() != null) {
            throw new IllegalArgumentException("Exam ID should not be provided for creation.");
        }
        return examRepository.save(exam);
    }

    @CachePut(value = "exams", key = "#exam.id")
    public Exam updateExam(Long id, @Valid @NotNull Exam exam) {
        Optional<Exam> optionalExistingExam = examRepository.findById(id);
        if (optionalExistingExam.isPresent()) {
            Exam existingExam = optionalExistingExam.get();
            existingExam.setExamName(exam.getExamName());
            return examRepository.save(existingExam);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found with ID: " + id);
        }
    }

    @Override
    @CacheEvict(value = "exams", key = "#id")
    public boolean deleteExam(Long id) {
        if (examRepository.existsById(id)) {
            examRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found with ID: " + id);
        }
    }
}
