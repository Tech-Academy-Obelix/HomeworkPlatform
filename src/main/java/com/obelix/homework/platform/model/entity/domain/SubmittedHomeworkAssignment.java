package com.obelix.homework.platform.model.entity.domain;

import com.obelix.homework.platform.model.dto.SubmittedHomeworkAssignmentDto;
import com.obelix.homework.platform.model.entity.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class SubmittedHomeworkAssignment extends HomeworkAssignment {
    private String solution;
    private Date timestamp;

    private String teacherComment;

    @OneToOne
    private Grade grade;

    @OneToOne
    private Student student;

    public SubmittedHomeworkAssignment(HomeworkAssignment homeworkAssignment, SubmittedHomeworkAssignmentDto submittedHomeworkAssignmentDto) {
        super(homeworkAssignment);
        this.solution = submittedHomeworkAssignmentDto.getSolution();
        this.timestamp = Date.from(Instant.now());
    }
}
