package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.config.exception.AssignmentNotFoundException;
import com.obelix.homework.platform.model.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String subjectName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Teacher> teachers;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Map<Teacher, Integer> coursesPerTeacher;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
        incrementCoursesForTeacher(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        if (coursesPerTeacher.get(teacher) > 0) {
            decrementCoursesForTeacher(teacher);
        }
        if (coursesPerTeacher.get(teacher) <= 0) {
            teachers.remove(teacher);
        }
    }

    private void incrementCoursesForTeacher(Teacher teacher) {
        coursesPerTeacher.put(teacher, coursesPerTeacher.getOrDefault(teacher, 0) + 1);
    }

    private void decrementCoursesForTeacher(Teacher teacher) {
        coursesPerTeacher.put(teacher, coursesPerTeacher.get(teacher) - 1);
    }
}
