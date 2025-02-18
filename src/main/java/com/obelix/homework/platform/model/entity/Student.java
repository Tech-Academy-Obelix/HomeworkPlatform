package com.obelix.homework.platform.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Student extends User {
    @OneToOne
    private Course course;
    @OneToMany
    private List<Grade> grades;

    public double getAverageGrade() {
        double sum = 0;
        for (Grade grade : grades) {
            sum += grade.getGrade();
        }
        return sum / grades.size();
    }

    public List<HomeworkAssignment> getHomeworkAssignments() {
        return course.getAssignments();
    }
}
