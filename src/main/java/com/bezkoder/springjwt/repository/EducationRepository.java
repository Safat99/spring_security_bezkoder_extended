package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository <Education,Long> {

    List<Education> findEducationByUserId(Long userId);
}
