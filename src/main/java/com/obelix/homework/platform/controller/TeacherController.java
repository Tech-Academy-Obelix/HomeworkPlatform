package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.model.dto.CourseDto;
import com.obelix.homework.platform.model.dto.HomeworkAssingmentDto;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.domain.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/assignments")
    public HomeworkAssignment createAssignment(HomeworkAssingmentDto dto) {
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

    @PostMapping("/assignments/{id}")
    public List<HomeworkAssignment> assignAssignmentToCourse(@PathVariable UUID id, @RequestBody CourseDto course) {
        return teacherService.assignAssignmentToCourse(id, course);
    }

    @GetMapping("/submitted-assignments")
    public List<SubmittedHomeworkAssignment> getSubmittedHomeworkAssignments() {
        return teacherService.getSubmittedHomeworkAssignments();
    }

    @GetMapping("/submitted-assignments/{id}")
    public SubmittedHomeworkAssignment getSubmittedAssignment(@PathVariable UUID id) {
        return teacherService.getSubmittedAssignment(id);
    }

}

