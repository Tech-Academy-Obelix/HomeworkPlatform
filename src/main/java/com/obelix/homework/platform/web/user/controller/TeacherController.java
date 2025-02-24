package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentCreateDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.web.user.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/assignments")
    public HomeworkAssignmentCreateDto createAssignment(@RequestBody HomeworkAssignmentCreateDto dto) {
        return teacherService.createAssignment(dto);
    }

    @GetMapping("/assignments")
    public List<HomeworkAssignment> getHomeworkAssignments() {
        return teacherService.getHomeworkAssignments();
    }

    @GetMapping("/assignments/{id}")
    public HomeworkAssignment getAssignment(@PathVariable UUID id) {
        return teacherService.getAssignment(id);
    }

    @PostMapping("/assignments/{id}/course/{courseId}")
    public List<HomeworkAssignment> assignAssignmentToCourse(@PathVariable UUID id, @PathVariable UUID courseId) {
        return teacherService.assignAssignmentToCourse(id, courseId);
    }

    @GetMapping("/submitted-assignments")
    public List<SubmittedHomeworkAssignment> getSubmittedHomeworkAssignments() {
        return teacherService.getSubmittedHomeworkAssignments();
    }

    @GetMapping("/submitted-assignments/{id}")
    public SubmittedHomeworkAssignment getSubmittedAssignment(@PathVariable UUID id) {
        return teacherService.getSubmittedAssignmentById(id);
    }
    @PostMapping("/submitted-assignments/{id}")
    public SubmittedHomeworkAssignment gradeSubmittedAssignments(@PathVariable UUID id, @RequestBody GradeDto grade){
        return teacherService.gradeSubmittedAssignment(id, grade);
    }

    @GetMapping("/courses")
    public List<Course> getCourses(){
        return teacherService.getCourses();
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable UUID id){
        return teacherService.getCourseById(id);
    }



}

