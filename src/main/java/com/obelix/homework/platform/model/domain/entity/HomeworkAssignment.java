package com.obelix.homework.platform.model.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HomeworkAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    private String name;
    private String description;

    private Date assignmentDate;
    private Date dueDate;

    @ManyToOne
    private Subject subject;

    public HomeworkAssignment(HomeworkAssignment homeworkAssignment) {
        id = UUID.randomUUID();
        name = homeworkAssignment.getName();
        description = homeworkAssignment.getDescription();
        assignmentDate = homeworkAssignment.getAssignmentDate();
        dueDate = homeworkAssignment.getDueDate();
        subject = homeworkAssignment.getSubject();
    }

}
