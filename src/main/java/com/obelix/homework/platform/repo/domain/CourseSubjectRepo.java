package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.CourseSubject;
import com.obelix.homework.platform.model.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseSubjectRepo extends JpaRepository<CourseSubject, UUID> {
    List<CourseSubject> findCourseSubjectsById(UUID id);

    List<Subject> getCourseSubjectsById(UUID id);

}
