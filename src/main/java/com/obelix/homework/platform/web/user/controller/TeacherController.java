package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.domain.dto.*;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.web.user.service.AIGradingService;
import com.obelix.homework.platform.web.user.service.PlagiarismDetectionService;
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
    private final AIGradingService aiGradingService;
    private final PlagiarismDetectionService plagiarismDetectionService;

    @GetMapping("/courses")
    public List<CourseDto> getCourses(){
        return teacherService.getCourses();
    }

    @GetMapping("/courses/{id}")
    public CourseDto getCourseById(@PathVariable UUID id){
        return teacherService.getCourseById(id);
    }

    @GetMapping("/courses/{id}/subjects")
    public List<SubjectDto> getSubjects(@PathVariable UUID id){
        return teacherService.getAllSubjectsInCourse(id);
    }

    @GetMapping("/assignments")
    public List<HomeworkAssignmentResponseDto> getHomeworkAssignments() {
        return teacherService.getAssignments();
    }

    @GetMapping("/assignments/{id}")
    public HomeworkAssignmentResponseDto getAssignment(@PathVariable UUID id) {
        return teacherService.getAssignmentById(id);
    }

    @PostMapping("/assignments")
    public HomeworkAssignmentResponseDto createAssignment(@RequestBody HomeworkAssignmentCreateDto assignment) {
        return teacherService.createAssignment(assignment);
    }

    @DeleteMapping("/assignments/{id}")
    public void deleteAssignment(@PathVariable UUID id) {
        teacherService.deleteAssignmentById(id);
    }

    @PostMapping("/course/{courseId}/subject/{subjectId}/assignment")
    public SubjectDto assignAssignmentToCourse(@PathVariable UUID courseId, @PathVariable UUID subjectId, @RequestBody UUID assignmentId) {
        return teacherService.assignAssignmentToSubjectInCourse(courseId, subjectId, assignmentId);
    }

    @GetMapping("/course/{courseId}/subject/{subjectId}/submitted-assignments")
    public List<SubmittedHomeworkAssignment> getSubmittedHomeworkAssignments(@PathVariable UUID courseId, @PathVariable UUID subjectId) {
        return teacherService.getSubmittedAssignmentsInCourseBySubjectId(courseId, subjectId);
    }

    @GetMapping("/course/{courseId}/subject/{subjectId}/submitted-assignments/{assignmentId}")
    public SubmittedHomeworkAssignment getSubmittedAssignment(@PathVariable UUID courseId, @PathVariable UUID subjectId, @PathVariable UUID assignmentId) {
        return teacherService.getSubmittedAssignmentInCourseInSubjectById(courseId, subjectId, assignmentId);
    }
    @PostMapping("/course/{courseId}/subject/{subjectId}/submitted-assignments/{assignmentId}")
    public SubmittedHomeworkAssignment gradeSubmittedAssignments(@PathVariable UUID courseId, @PathVariable UUID subjectId, @PathVariable UUID assignmentId, @RequestBody GradeDto grade){
        return teacherService.gradeSubmittedAssignment(courseId, subjectId, assignmentId, grade);
    }

    @GetMapping("/course/{courseId}/subject/{subjectId}/submitted-assignments/{assignmentId}/ai-grade")
    public GradeDto suggestGradeWithAI(@PathVariable UUID courseId, @PathVariable UUID subjectId, @PathVariable UUID assignmentId) {
        return aiGradingService.gradeSubmissionWithAI(
                        teacherService.getSubmittedAssignmentInCourseInSubjectById(courseId, subjectId, assignmentId).getSolution());
    }

    @GetMapping("/course/{courseId}/subject/{subjectId}/submitted-assignments/{assignmentId}/plagiarism-check")
    public String checkPlagiarism(@PathVariable UUID courseId, @PathVariable UUID subjectId, @PathVariable UUID assignmentId) {
        return String.format("Plagiarism detected: %.2f%% similarity.",
                plagiarismDetectionService.checkPlagiarism(teacherService.getSubmittedAssignmentInCourseInSubjectById(courseId, subjectId, assignmentId)) * 100);
    }
}

