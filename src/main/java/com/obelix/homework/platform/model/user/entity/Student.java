package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    public Student(User user) {
        super(user);
    }
}
