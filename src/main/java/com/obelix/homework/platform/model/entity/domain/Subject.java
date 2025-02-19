package com.obelix.homework.platform.model.entity.domain;

import com.obelix.homework.platform.model.entity.users.Teacher;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String subjectName;

    @OneToOne
    private Teacher teacher;
}
