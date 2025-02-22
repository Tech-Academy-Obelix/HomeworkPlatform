package com.obelix.homework.platform.model.entity.user;

import com.obelix.homework.platform.model.entity.domain.Course;
import com.obelix.homework.platform.model.entity.domain.CourseSubject;
import com.obelix.homework.platform.model.entity.domain.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
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

    public Course getOwnCourse() {
        return null;
    }
}