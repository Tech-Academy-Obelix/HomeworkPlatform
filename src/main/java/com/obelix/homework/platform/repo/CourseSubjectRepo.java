package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.CourseSubject;
import com.obelix.homework.platform.model.entity.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseSubjectRepo extends JpaRepository<CourseSubject, UUID> {
    List<CourseSubject> findCourseSubjectsByCourse(Course course);

    List<CourseSubject> findCourseSubjectsById(UUID id);

    List<Subject> getCourseSubjectsById(UUID id);
}
