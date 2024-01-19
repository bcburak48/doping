package com.doping.burak.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ad alanı boş olamaz")
    private String firstName;

    @NotBlank(message = "Soyad alanı boş olamaz")
    private String lastName;

    @NotNull(message = "Öğrenci numarası alanı boş olamaz")
    @Size(min = 8, max = 10, message = "Öğrenci numarası 8 ile 10 karakter arasında olmalıdır")
    private String studentNumber;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StudentExam> studentExams = new HashSet<>();
}
