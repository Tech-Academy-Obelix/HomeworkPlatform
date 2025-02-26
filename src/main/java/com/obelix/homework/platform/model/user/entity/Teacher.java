package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> courses;

    public Course getCourseById(UUID courseId) {
        return courses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));
    }

    public Teacher(User user) {
        super(user);
    }
}