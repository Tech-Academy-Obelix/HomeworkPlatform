package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentStudentDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.Submission;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.repo.domain.SubmittedHomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserService userService;
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public List<HomeworkAssignmentStudentDto> getHomeworkAssignments() {
        return student().getHomeworkAssignments().stream()
                .map(this::getAssignmentDto)
                .collect(Collectors.toList());
    }

    public HomeworkAssignmentStudentDto getHomeworkAssignmentDtoById(UUID id) {
        return getHomeworkAssignments().stream()
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public Submission submitAssignment(UUID id, String solution) {
        var student = student();
        var submission = submittedHomeworkAssignmentRepo.save(new Submission(solution, getHomeworkAssignmentById(id)));
        student.addSubmission(submission);
        userRepo.save(student);
        return submission;
    }

    public List<Submission> submitBulkAssignments(List<SubmissionDto> submissionDtos) {
        return submissionDtos.stream()
                .map(submissionDto -> submitAssignment(submissionDto.getId(), submissionDto.getSolution()))
                .collect(Collectors.toList());
    }

    public List<Submission> getSubmittedAssignments() {
        return student().getSubmissions();
    }

    public Submission getSubmittedAssignment(UUID id) {
        return student().getSubmissions().stream()
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public List<Grade> getGrades() {
        return student().getGrades();
    }

    private Student student() {
        return userService.getLoggedInStudent();
    }

    private HomeworkAssignment getHomeworkAssignmentById(UUID id) {
        return student().getHomeworkAssignments().stream()
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    private HomeworkAssignmentStudentDto getAssignmentDto(HomeworkAssignment assignment) {
        var dto = modelMapper.map(assignment, HomeworkAssignmentStudentDto.class);
        dto.setSubjectDto(modelMapper.map(student().getCourse().getSubjectByAssignment(assignment), SubjectDto.class));
        return dto;
    }
}
