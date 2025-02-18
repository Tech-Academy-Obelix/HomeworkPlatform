package com.obelix.homework.platform.model.entity;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SubmittedHomeworkAssignment extends HomeworkAssignment {
    private String solution;
    private Date timestamp;

    public SubmittedHomeworkAssignment(HomeworkAssignment homeworkAssignment, SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        super(homeworkAssignment);
        this.solution = submittedHomeworkAssignmentDto.getSolution();
        this.timestamp = submittedHomeworkAssignmentDto.getTimestamp();
    }
}
