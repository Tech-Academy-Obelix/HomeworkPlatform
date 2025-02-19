package com.obelix.homework.platform.model.entity.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HomeworkAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    private String assignmentName;
    private String assignmentDescription;

    private Date assignmentDate;
    private Date dueDate;

    @ManyToOne
    private Subject subject;

    public HomeworkAssignment(HomeworkAssignment homeworkAssignment) {
        id = UUID.randomUUID();
        assignmentName = homeworkAssignment.getAssignmentName();
        assignmentDescription = homeworkAssignment.getAssignmentDescription();
        assignmentDate = homeworkAssignment.getAssignmentDate();
        dueDate = homeworkAssignment.getDueDate();
        subject = homeworkAssignment.getSubject();
    }

}
