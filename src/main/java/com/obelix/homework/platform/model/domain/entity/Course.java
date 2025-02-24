package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.model.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @ManyToMany
    private List<Subject> subjects;

    @OneToMany
    private Map<Subject, Teacher> subjectTeachers;

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

    public Subject getSubjectById(UUID subjectId) {
        return subjects.stream().filter(subject -> subject.getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
    }

    public void removeSubjectById(UUID subjectId) {
        subjects.remove(subjects.stream().filter(subject -> subject.getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString())));
    }
}
