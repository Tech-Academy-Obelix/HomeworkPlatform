package com.obelix.homework.platform.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    private String subjectName;

    @OneToOne
    private Teacher teacher;
}
