package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Grade;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.Submission;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Grade> grades;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Submission> submissions;

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

    public void addSubmission(Submission submission) {
        submission.setStudent(this);
        submissions.add(submission);
    }
}
