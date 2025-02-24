package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.model.user.entity.Teacher;
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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String subjectName;

    private int assignedTeachers;

    @ManyToMany
    private List<Teacher> teachers;

    @OneToMany
    private List<CourseSubject> courseSubjects;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        assignedTeachers++;
    }

    public void unassignTeacher() {
        assignedTeachers--;
    }
}
