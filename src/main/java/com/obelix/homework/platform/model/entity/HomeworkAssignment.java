package com.obelix.homework.platform.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public HomeworkAssignment(HomeworkAssignment homeworkAssignment) {
        this.id = homeworkAssignment.getId();
        this.assignmentName = homeworkAssignment.getAssignmentName();
        this.assignmentDescription = homeworkAssignment.getAssignmentDescription();
        this.assignmentDate = homeworkAssignment.getAssignmentDate();
        this.dueDate = homeworkAssignment.getDueDate();
    }

}
