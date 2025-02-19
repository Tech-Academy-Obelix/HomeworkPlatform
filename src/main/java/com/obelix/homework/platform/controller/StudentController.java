package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.entity.domain.Grade;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.domain.SubmittedHomeworkAssignment;
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

    @GetMapping()
    public String student() {
        return "Hello Student";
    }

    @GetMapping("/assignments")
    public List<HomeworkAssignment> getAssignments() {
        return studentService.getAssignments();
    }

    @GetMapping("/assignments/{id}")
    public HomeworkAssignment getAssignment(@PathVariable UUID id) {
        return studentService.getAssignment(id);
    }

    @PostMapping("/assignments")
    public SubmittedHomeworkAssignment submitAssignment(@RequestBody SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        return studentService.submitAssignment(submittedHomeworkAssignmentDto);
    }

    @PostMapping("/assignments/bulk")
    public List<SubmittedHomeworkAssignment> submitBulkAssignments(@RequestBody List<SubmittedHomeworkAssignmentDto> submittedHomeworkAssignmentDtos) {
        return studentService.submitBulkAssignments(submittedHomeworkAssignmentDtos);
    }

    @GetMapping("/submitted-assignments")
    public List<SubmittedHomeworkAssignment> getSubmittedAssignments() {
        return studentService.getSubmittedAssignments();
    }

    @GetMapping("/submitted-assignments/{id}")
    public SubmittedHomeworkAssignment getSubmittedAssignment(@PathVariable UUID id) {
        return studentService.getSubmittedAssignment(id);
    }

    @GetMapping("/grades")
    public List<Grade> getGrades() {
        return studentService.getGrades();
    }
}
