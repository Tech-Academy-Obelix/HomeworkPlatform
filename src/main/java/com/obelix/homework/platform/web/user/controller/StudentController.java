package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentStudentDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionInBulkCreateDto;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.Submission;
import com.obelix.homework.platform.web.user.service.StudentService;
import com.obelix.homework.platform.web.user.service.AIGradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final AIGradingService aiGradingService;

    @GetMapping("/assignments")
    public List<HomeworkAssignmentStudentDto> getAssignments() {
        return studentService.getHomeworkAssignments();
    }

    @GetMapping("/assignments/{id}")
    public HomeworkAssignmentStudentDto getAssignment(@PathVariable UUID id) {
        return studentService.getHomeworkAssignmentDtoById(id);
    }

    @PostMapping("/assignments/{id}")
    public SubmissionDto submitAssignment(@PathVariable UUID id, @RequestBody String solution) {
        return studentService.submitAssignment(id, solution);
    }

    @PostMapping("/assignments/bulk")
    public List<SubmissionDto> submitBulkAssignments(@RequestBody List<SubmissionInBulkCreateDto> submissionInBulkDtos) {
        return studentService.submitBulkAssignments(submissionInBulkDtos);
    }

    @GetMapping("/submitted-assignments")
    public List<SubmissionDto> getSubmittedAssignments() {
        return studentService.getSubmittedAssignments();
    }

    @GetMapping("/submitted-assignments/{id}")
    public SubmissionDto getSubmittedAssignment(@PathVariable UUID id) {
        return studentService.getSubmittedAssignmentById(id);
    }

    @GetMapping("/grades")
    public List<Grade> getGrades() {
        return studentService.getGrades();
    }

    @PostMapping("/assignments/ai-grade")
    public GradeDto submitForAIAssessment(@RequestBody Submission submittedHomeworkAssignmentDto) {
        return aiGradingService.gradeSubmissionWithAI(submittedHomeworkAssignmentDto.getSolution());
    }
}

