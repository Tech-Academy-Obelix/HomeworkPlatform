package com.obelix.homework.platform.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Student extends User {
    @OneToOne
    private Course course;

    @OneToMany
    private List<Grade> grades;

    @OneToMany
    private List<SubmittedHomeworkAssignment> submittedHomeworkAssignments;

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
