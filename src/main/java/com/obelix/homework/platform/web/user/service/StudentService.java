package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.config.exception.SubmissionNotFoundException;
import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentStudentDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionInBulkCreateDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.Submission;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.repo.domain.CourseRepo;
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
    private final CourseRepo courseRepo;

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

    public SubmissionDto submitAssignment(UUID id, String solution) {
        var student = student();
        var assignment = getHomeworkAssignmentById(id);
        var course = student.getCourse();
        var submission = submittedHomeworkAssignmentRepo.save(new Submission(solution, assignment));
        course.addSubmission(student.getId(), submission);
        courseRepo.save(course);
        var dto = modelMapper.map(submission, SubmissionDto.class);
        dto.setHomeworkAssignment(getAssignmentDto(assignment));
        userRepo.save(student);
        return dto;
    }

    public List<SubmissionDto> submitBulkAssignments(List<SubmissionInBulkCreateDto> submissionDtos) {
        return submissionDtos.stream()
                .map(submissionDto -> submitAssignment(submissionDto.getId(), submissionDto.getSolution()))
                .collect(Collectors.toList());
    }

    public List<SubmissionDto> getSubmittedAssignments() {
        return student().getSubmissions().stream()
                .map(submission -> modelMapper.map(submission, SubmissionDto.class))
                .collect(Collectors.toList());
    }

    public SubmissionDto getSubmittedAssignmentById(UUID id) {
        return getSubmittedAssignments().stream()
                .filter(submissionDto -> submissionDto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new SubmissionNotFoundException(id.toString()));
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
