package com.obelix.homework.platform.model.entity;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Entity
@NoArgsConstructor

public class SubmittedHomeworkAssignment extends HomeworkAssignment {
    private String solution;
    private Date timestamp;

    private String teacherComment;

    @OneToOne
    private Grade grade;

    public SubmittedHomeworkAssignment(HomeworkAssignment homeworkAssignment, SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        super(homeworkAssignment);
        this.solution = submittedHomeworkAssignmentDto.getSolution();
        this.timestamp = Date.from(Instant.now());
    }
}
