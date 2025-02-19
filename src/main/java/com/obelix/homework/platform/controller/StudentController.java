package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.entity.Grade;
import com.obelix.homework.platform.model.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/assignments")
    public List<HomeworkAssignment> getAssignments() {
        return studentService.getAssignments();
    }

    @GetMapping("/assignments/{id}")
    public HomeworkAssignment getAssignment(@PathVariable UUID id) {
        return studentService.getAssignment(id);
    }

    @PostMapping("/assignments/{id}")
    public SubmittedHomeworkAssignment submitAssignment(@PathVariable UUID id, @RequestBody SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        return studentService.submitAssignment(id, submittedHomeworkAssignmentDto);
    }

    @GetMapping("/submitted-assignments")
    public List<SubmittedHomeworkAssignment> getSubmittedAssignments() {
        return studentService.getSubmittedAssignments();
    }

    @GetMapping("/submitted-assignments/{id}")
    public SubmittedHomeworkAssignment getSubmittedAssignment(@PathVariable UUID id) {
        return studentService.getSubmittedAssignment(id);
    }

    @PostMapping("/grades")
    public List<Grade> getGrades() {
        return studentService.getGrades();
    }
}
