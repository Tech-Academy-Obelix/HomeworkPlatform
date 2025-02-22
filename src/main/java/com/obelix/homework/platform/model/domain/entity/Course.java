package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.model.user.entity.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String courseName;

    @OneToMany
    private List<Student> students;

    @Transient
    private List<Subject> subjects;

    @OneToMany
    private List<CourseSubject> courseSubjects;

    @OneToMany
    private List<HomeworkAssignment> assignments;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public double getAverageGrade() {
        double sum = 0;
        for (Student student : students) {
            sum += student.getAverageGrade();
        }
        return sum / students.size();
    }
}
