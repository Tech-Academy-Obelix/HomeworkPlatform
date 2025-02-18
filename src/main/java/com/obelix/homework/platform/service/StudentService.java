package com.obelix.homework.platform.service;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.entity.Student;
import com.obelix.homework.platform.model.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.repo.HomeworkAssignmentRepo;
import com.obelix.homework.platform.repo.SubmittedHomeworkAssignmentRepo;
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
    private final Student student = (Student) userDetailsService.getLoggedInUser();

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
}
