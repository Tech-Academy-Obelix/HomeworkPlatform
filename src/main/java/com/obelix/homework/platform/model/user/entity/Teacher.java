package com.obelix.homework.platform.model.user.entity;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.config.exception.CourseNotFoundException;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.ast.tree.update.Assignment;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Course> courses;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HomeworkAssignment> assignments;

    public Course getCourseById(UUID courseId) {
        return courses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));
    }

    public void addAssignment(HomeworkAssignment homeworkAssignment) {
        assignments.add(homeworkAssignment);
    }

    public HomeworkAssignment getAssignmentById(UUID id) {
        return assignments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(id.toString()));
    }

    public void removeAssignmentById(UUID id) {
        assignments.removeIf(assignment -> assignment.getId().equals(id));
    }

    public Teacher(User user) {
        super(user);
    }
}