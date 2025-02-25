package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends User {
    @ManyToMany
    private List<Subject> subjects;

    @ManyToMany
    private List<Course> courses;

    public Teacher(User user) {
        super(user);
    }
}