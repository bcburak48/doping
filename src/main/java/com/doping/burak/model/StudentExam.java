package com.doping.burak.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class StudentExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private Date examDate;

    private boolean isActive;

    @OneToMany(mappedBy = "studentExam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StudentAnswer> studentAnswers = new HashSet<>();

    private int score;

}