package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepo extends JpaRepository<Course, UUID> {
}
