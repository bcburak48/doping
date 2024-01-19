package com.doping.burak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamReport {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
}
