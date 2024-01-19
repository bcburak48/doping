package com.doping.burak.repository;

import com.doping.burak.model.StudentAnswer;
import com.doping.burak.model.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    boolean existsByStudent_IdAndQuestion_Id(Long studentId, Long questionId);

    List<StudentAnswer> findByStudentExam_Exam_IdAndStudentExam_Student_Id(Long examId, Long studentId);
}
