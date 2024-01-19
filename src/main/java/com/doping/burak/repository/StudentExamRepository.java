package com.doping.burak.repository;

import com.doping.burak.model.Exam;
import com.doping.burak.model.Student;
import com.doping.burak.model.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {
    List<StudentExam> findByStudentAndIsActive(Student student, boolean isActive);

}
