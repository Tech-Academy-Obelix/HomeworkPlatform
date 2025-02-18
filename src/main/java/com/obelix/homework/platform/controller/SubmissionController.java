package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.model.Submission;
import com.obelix.homework.platform.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public Submission submitAssignment(@RequestBody Submission submission) {
        return submissionService.submitAssignment(submission);
    }

    @PostMapping("/bulk")
    public List<Submission> submitBulkAssignments(@RequestBody List<Submission> submissions) {
        return submissionService.submitBulkAssignments(submissions);
    }

    @GetMapping("/student/{username}")
    public List<Submission> getSubmissionsByStudent(@PathVariable String username) {
        return submissionService.getSubmissionsByStudent(username);
    }

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }
}
