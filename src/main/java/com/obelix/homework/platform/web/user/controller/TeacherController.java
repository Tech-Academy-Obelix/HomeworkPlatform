package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentCreateDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.web.user.service.TeacherService;
import com.obelix.homework.platform.web.user.service.AIGradingAPIService;
import com.obelix.homework.platform.web.user.service.PlagiarismDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final AIGradingAPIService aiGradingAPIService;
    private final PlagiarismDetectionService plagiarismDetectionService;

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

    @GetMapping("/submitted-assignments/{id}/ai-grade")
    public String suggestGradeWithAI(@PathVariable UUID id) {
        // 1️⃣ Fetch the submission by ID
        SubmittedHomeworkAssignment submission = teacherService.getSubmittedAssignmentById(id);

        if (submission == null) {
            return "Submission not found!";
        }

        String aiSuggestedGrade = aiGradingAPIService.gradeSubmissionWithAI(submission.getSolution());

        return String.format("AI Suggests: %s. Teacher must finalize the grade.", aiSuggestedGrade);
    }

    @GetMapping("/submitted-assignments/{id}/plagiarism-check")
    public String checkPlagiarism(@PathVariable UUID id) {
        // 1️⃣ Fetch the current submission
        SubmittedHomeworkAssignment submission = teacherService.getSubmittedAssignmentById(id);
        if (submission == null) {
            return "Submission not found!";
        }

        List<String> existingSubmissions = teacherService.getSubmittedHomeworkAssignments().stream()
                .filter(s -> !s.getId().equals(id))
                .map(SubmittedHomeworkAssignment::getSolution)
                .collect(Collectors.toList());

        double similarityScore = plagiarismDetectionService.checkPlagiarism(submission.getSolution(), existingSubmissions);

        return String.format("Plagiarism detected: %.2f%% similarity. Teacher must decide if it's acceptable.", similarityScore * 100);
    }
}
