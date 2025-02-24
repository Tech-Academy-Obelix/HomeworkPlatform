package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.SubjectDto;
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
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourseDtos();
    }

    @GetMapping("/{id}")
    public CourseDto getCourse(@PathVariable UUID id) {
        return courseService.getCourseDtoById(id);
    }

    @PostMapping
    public CourseDto createCourse(@RequestBody String courseName) {
        return courseService.createCourse(courseName);
    }

    @PostMapping("/bulk")
    public List<CourseDto> createCourses(@RequestBody List<String> courseNames) {
        return courseService.createCourses(courseNames);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourseById(id);
    }

    @PutMapping("/{id}/subjects")
    public CourseDto addSubject(@PathVariable UUID id, @RequestBody UUID subjectId) {
        return courseService.addSubjectToCourse(id, subjectId);
    }

    @PutMapping("/{id}/subjects/bulk")
    public CourseDto addSubjects(@PathVariable UUID id, @RequestBody List<UUID> subjectIds) {
        return courseService.addSubjectsToCourse(id, subjectIds);
    }

    @GetMapping("/{id}/subjects")
    public List<SubjectDto> getSubjectsInCourse(@PathVariable UUID id) {
        return courseService.getSubjectsInCourse(id);
    }

    @DeleteMapping("/{id}/subjects/{subjectId}")
    public CourseDto removeSubject(@PathVariable UUID id, @PathVariable UUID subjectId) {
        return courseService.removeSubjectFromCourse(id, subjectId);
    }

    @PutMapping("/{id}/subjects/{subjectId}/teachers")
    public CourseDto addTeacherToSubjectInCourse(@PathVariable UUID id, @PathVariable UUID subjectId, @RequestBody UUID teacherId) {
        return courseService.addTeacherToSubjectInCourse(id, subjectId, teacherId);
    }

    @DeleteMapping("/{id}/subjects/{subjectId}/teachers")
    public CourseDto removeTeacherFromSubjectInCourse(@PathVariable UUID id, @PathVariable UUID subjectId, @RequestBody UUID teacherId) {
        return courseService.removeTeacherFromSubjectInCourse(id, subjectId, teacherId);
    }
}
