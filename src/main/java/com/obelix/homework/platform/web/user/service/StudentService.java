package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.model.domain.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.repo.domain.HomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.domain.SubmittedHomeworkAssignmentRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserService userService;
    private final HomeworkAssignmentRepo homeworkAssignmentRepo;
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;
    private Student student;

    public List<HomeworkAssignment> getAssignments() {
        return student.getHomeworkAssignments();
    }

    public HomeworkAssignment getAssignment(UUID id) {
        return student.getCourse().getAssignments().stream()
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public SubmittedHomeworkAssignment submitAssignment(SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        return submittedHomeworkAssignmentRepo.save(new SubmittedHomeworkAssignment(
                homeworkAssignmentRepo.getHomeworkAssignmentById(submittedHomeworkAssignmentDto.getId()),
                submittedHomeworkAssignmentDto));
    }

    public List<SubmittedHomeworkAssignment> submitBulkAssignments(List<SubmittedHomeworkAssignmentDto> submittedHomeworkAssignmentDtos) {
        return submittedHomeworkAssignmentRepo.saveAll(
                submittedHomeworkAssignmentDtos.stream()
                .map(this::submitAssignment) // For each DTO, call submitAssignment and map to a Saved Assignment
                .collect(Collectors.toList())); // Collect the results into a List
    }

    public List<SubmittedHomeworkAssignment> getSubmittedAssignments() {
        return student.getSubmittedHomeworkAssignments();
    }

    public SubmittedHomeworkAssignment getSubmittedAssignment(UUID id) {
        return student.getSubmittedHomeworkAssignments().stream()
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public List<Grade> getGrades() {
        return student.getGrades();
    }

    @PostConstruct
    public void init() {
        student = (Student) userService.getLoggedInUser();
    }
}
