package com.obelix.homework.platform.model.entity.user;

import com.obelix.homework.platform.model.entity.domain.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Teacher extends User {
    @OneToOne
    private Course ownCourse;

    @OneToMany
    private List<Course> courses;
}