package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {
    @OneToOne
    private Course course;

    @OneToMany
    private List<Grade> grades;

    @OneToMany
    private List<SubmittedHomeworkAssignment> submittedHomeworkAssignments;

    public Double getAverageGrade() {
        if (grades == null) return null;
        double sum = 0;
        for (Grade grade : grades) {
            sum += grade.getGrade();
        }
        return sum / grades.size();
    }

    public List<HomeworkAssignment> getHomeworkAssignments() {
        if (course == null) return null;
        return course.getAssignments();
    }

    public Student(User user) {
        super(user);
    }
}
