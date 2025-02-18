package com.obelix.homework.platform.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Teacher extends User {
    @OneToOne
    private Course ownCourse;

    @OneToMany
    private List<Course> courses;
}
