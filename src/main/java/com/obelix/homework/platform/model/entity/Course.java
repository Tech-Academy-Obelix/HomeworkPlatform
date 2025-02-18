package com.obelix.homework.platform.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.sql.ast.tree.update.Assignment;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String courseName;
    @OneToMany
    private List<Student> students;
    @OneToOne
    private Teacher teacher;
    @OneToMany
    private List<Subject> subjects;
    @OneToMany
    private List<HomeworkAssignment> assignments;

    private double getAverageGrade() {
        double sum = 0;
        for (Student student : students) {
            sum += student.getAverageGrade();
        }
        return sum / students.size();
    }
}
