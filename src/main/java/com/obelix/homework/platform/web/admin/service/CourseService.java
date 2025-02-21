package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.CourseSubject;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;
    private final SubjectRepo subjectRepo;
    private final TeacherRepo teacherRepo;
    private final CourseSubjectRepo courseSubjectRepo;

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public Course getCourseById(UUID courseId) {
        return courseRepo.getCourseById(courseId);
    }

    public Course createCourse(String courseName) {
        return courseRepo.save(new Course(courseName));
    }

    public void deleteCourseById(UUID courseId) {
        courseRepo.deleteById(courseId);
    }

    public Course addSubjectToCourse(UUID courseId, UUID subjectId) {
        return courseSubjectRepo.save(CourseSubject.builder()
                .course(courseRepo.getCoursesById(courseId))
                .subject(subjectRepo.getSubjectById(subjectId))
                .build()).getCourse();
    }

    public List<Subject> getSubjectsInCourse(UUID courseId) {
        return courseSubjectRepo.getCourseSubjectsById(courseId);
    }

    public void removeSubjectFromCourse(UUID courseId, UUID subjectId) {
        courseSubjectRepo.delete(
                courseSubjectRepo.findCourseSubjectsById(courseId).stream()
                        .filter(courseSubject -> courseSubject.getSubject().getId().equals(subjectId))
                        .findFirst()
                        .orElseThrow(() -> new SubjectNotFoundException("Subject not found")));
    }

    public Course addTeacherToSubjectInCourse(UUID courseId, UUID subjectId, UUID teacherId) {
        var courseSubject = courseSubjectRepo.findCourseSubjectsById(courseId).stream()
                .filter(courseSubject1 -> courseSubject1.getSubject().getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
        courseSubject.setTeacher(teacherRepo.getTeacherById(teacherId));
        return courseSubjectRepo.save(courseSubject).getCourse();
    }
}
