package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.web.admin.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable UUID id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course createCourse(@RequestBody String courseName) {
        return courseService.createCourse(courseName);
    }

    @PostMapping("/bulk")
    public List<Course> createCourses(@RequestBody List<String> courseNames) {
        return courseService.createCourses(courseNames);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourseById(id);
    }

    @PutMapping("/{id}/subjects/{subjectId}")
    public Course addSubject(@PathVariable UUID id, @PathVariable UUID subjectId) {
        return courseService.addSubjectToCourse(id, subjectId);
    }

    @PutMapping("/{id}/subjects")
    public Course addSubjects(@PathVariable UUID id, @RequestBody List<UUID> subjectIds) {
        return courseService.addSubjectsToCourse(id, subjectIds);
    }

    @GetMapping("/{id}/subjects")
    public List<Subject> getSubjectsInCourse(@PathVariable UUID id) {
        return courseService.getSubjectsInCourse(id);
    }

    @DeleteMapping("/{id}/subjects/{subjectId}")
    public Course removeSubject(@PathVariable UUID id, @PathVariable UUID subjectId) {
        return courseService.removeSubjectFromCourse(id, subjectId);
    }

    @PutMapping("/{id}/subjects/{subjectId}/teachers/{teacherId}")
    public Course addTeacherToSubjectInCourse(@PathVariable UUID id, @PathVariable UUID subjectId, @PathVariable UUID teacherId) {
        return courseService.addTeacherToSubjectInCourse(id, subjectId, teacherId);
    }
}
