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
import java.util.stream.Collectors;

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
        return course.getSubjects().stream()
                .flatMap(subject -> subject.getAssignments().stream()) // Flatten the stream
                .collect(Collectors.toList()); // Collect into a single list
    }

    public Student(User user) {
        super(user);
    }
}
