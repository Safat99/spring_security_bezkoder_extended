package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository <Education,Long> {

}
