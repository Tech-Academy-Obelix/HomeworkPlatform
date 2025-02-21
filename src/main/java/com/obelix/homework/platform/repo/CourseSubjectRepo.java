package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.CourseSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseSubjectRepo extends JpaRepository<CourseSubject, UUID> {
    List<CourseSubject> findCourseSubjectByCourse(Course course);
}
