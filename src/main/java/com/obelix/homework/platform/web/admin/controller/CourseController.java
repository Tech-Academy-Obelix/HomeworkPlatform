package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.web.admin.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{id}")
    public Course getCourse(@PathVariable UUID id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/courses/{courseName}")
    public Course createCourse(@PathVariable String courseName) {
        return courseService.createCourse(courseName);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourseById(id);
    }

    @PutMapping("/courses/{id}/subjects/{subjectId}")
    public Course addSubject(@PathVariable UUID id, @PathVariable UUID subjectId) {
        return courseService.addSubjectToCourse(id, subjectId);
    }

    @GetMapping("/courses/{id}/subjects")
    public List<Subject> getSubjectsInCourse(@PathVariable UUID id) {
        return courseService.getSubjectsInCourse(id);
    }

    @DeleteMapping("/courses/{id}/subjects/{subjectId}")
    public void removeSubject(@PathVariable UUID id, @PathVariable UUID subjectId) {
        courseService.removeSubjectFromCourse(id, subjectId);
    }

    @PutMapping("/courses/{id}/subjects/{subjectId}/teachers/{teacherId}")
    public Course addTeacherToSubjectInCourse(@PathVariable UUID id, @PathVariable UUID subjectId, @PathVariable UUID teacherId) {
        return courseService.addTeacherToSubjectInCourse(id, subjectId, teacherId);
    }

    @DeleteMapping("/courses/{id}/subjects/{subjectId}/teachers/{teacherId}")
    public void removeTeacherFromSubjectInCourse(@PathVariable UUID id, @PathVariable UUID subjectId, @PathVariable UUID teacherId) {
        courseService.removeTeacherFromSubjectInCourse();
    }
}
