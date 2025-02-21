package com.obelix.homework.platform.model.entity.domain;

import com.obelix.homework.platform.model.entity.user.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String subjectName;

    @ManyToMany
    private List<Teacher> teachers;

    @OneToMany
    private List<CourseSubject> courseSubjects;
}
