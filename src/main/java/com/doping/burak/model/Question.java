package com.doping.burak.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Soru metni boş olamaz")
    private String questionText;

    @NotBlank(message = "Sorunun cevabı boş olamaz")
    private String questionAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    @NotNull(message = "Soru bir sınava ait olmalıdır")
    private Exam exam;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StudentAnswer> studentAnswers = new HashSet<>();

    public Question(Long l, String s, String s1) {
    }
}
