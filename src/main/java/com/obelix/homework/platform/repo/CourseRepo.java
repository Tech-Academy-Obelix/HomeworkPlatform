package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepo extends JpaRepository<Course, UUID> {
}
