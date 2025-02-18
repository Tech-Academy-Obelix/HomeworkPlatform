package com.obelix.homework.platform.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity
public class HomeworkAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    private String assignmentName;
    private String assignmentDescription;

    private Date assignmentDate;
    private Date dueDate;

}
