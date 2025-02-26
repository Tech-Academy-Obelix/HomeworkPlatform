package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.web.user.service.StudentService;
import com.obelix.homework.platform.web.user.service.AIGradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final AIGradingService aiGradingService;
/*
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
    */

    @PostMapping("/assignments/ai-grade")
    public GradeDto submitForAIAssessment(@RequestBody SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        return aiGradingService.gradeSubmissionWithAI(submittedHomeworkAssignmentDto.getSolution());
    }

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "imageupload/index";
    }
}

