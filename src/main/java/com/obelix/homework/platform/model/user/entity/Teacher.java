package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.CourseSubject;
import com.obelix.homework.platform.model.domain.entity.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends User {
    @OneToMany
    private List<CourseSubject> courseSubjects;

    @Transient
    private List<Subject> subjects;

    @Transient
    private List<Course> courses;

    @PostLoad
    public void setCoursesAndSubjects() {
        courses = courseSubjects.stream()
                .map(CourseSubject::getCourse)
                .collect(Collectors.toList());
        subjects = courseSubjects.stream()
                .map(CourseSubject::getSubject)
                .collect(Collectors.toList());
    }

    public Teacher(User user) {
        super(user);
    }
}