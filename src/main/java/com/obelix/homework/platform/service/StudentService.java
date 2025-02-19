package com.obelix.homework.platform.service;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.entity.Grade;
import com.obelix.homework.platform.model.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.Student;
import com.obelix.homework.platform.model.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.repo.HomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.SubmittedHomeworkAssignmentRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserDetailsService userDetailsService;
    private final HomeworkAssignmentRepo homeworkAssignmentRepo;
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;
    private Student student;

    public List<HomeworkAssignment> getAssignments() {
        return student.getHomeworkAssignments();
    }

    public HomeworkAssignment getAssignment(UUID id) {
        return homeworkAssignmentRepo.getHomeworkAssignmentById(id);
    }

    public SubmittedHomeworkAssignment submitAssignment(UUID id, SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        return submittedHomeworkAssignmentRepo.save(new SubmittedHomeworkAssignment(
                homeworkAssignmentRepo.getHomeworkAssignmentById(id),
                submittedHomeworkAssignmentDto
        ));
    }

    public List<SubmittedHomeworkAssignment> getSubmittedAssignments() {
        return student.getSubmittedHomeworkAssignments();
    }

    public SubmittedHomeworkAssignment getSubmittedAssignment(UUID id) {
        return submittedHomeworkAssignmentRepo.getSubmittedHomeworkAssignmentById(id);
    }

    public List<Grade> getGrades() {
        return student.getGrades();
    }

    @PostConstruct
    public void init() {
        student = (Student) userDetailsService.getLoggedInUser();
    }
}
