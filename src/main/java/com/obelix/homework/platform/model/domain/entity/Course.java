package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.config.exception.SubjectHasAssignedTeacherException;
import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.config.exception.UserNotFoundException;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.model.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
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

    @ManyToMany
    private List<Subject> subjects;

    @OneToMany
    private Map<Subject, Teacher> subjectTeachers;

    @OneToMany
    private List<HomeworkAssignment> assignments;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Double getAverageGrade() {
        double sum = 0;
        for (Student student : students) {
            var grade = student.getAverageGrade();
            sum += grade == null ? 0 : grade;
        }
        return sum / students.size();
    }

    public Subject getSubjectById(UUID subjectId) {
        return subjects.stream().filter(subject -> subject.getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new SubjectNotFoundException(subjectId.toString()));
    }

    public void removeSubjectById(UUID subjectId) {
        subjects.removeIf(subject -> subject.getId().equals(subjectId));
    }

    public void addTeacherToSubject(Teacher teacher, UUID subjectId) {
        var subject = getSubjectById(subjectId);
        throwIfTeacherAssigned(subject);
        subjectTeachers.put(subject, teacher);
        subject.addTeacher(teacher);
    }

    public void removeTeacherFromSubject(UUID teacherId, UUID subjectId) {
        var subject = getSubjectById(subjectId);
        subjectTeachers.remove(subject);
        subject.removeTeacher(getTeacherById(teacherId));
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setCourse(this);
    }

    public void removeStudentById(UUID studentId) {
        var student = getStudentById(studentId);
        students.remove(student);
        student.setCourse(null);
    }

    private Student getStudentById(UUID studentId) {
        return students.stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(studentId.toString()));
    }

    private Teacher getTeacherById(UUID teacherId) {
        return subjectTeachers.values().stream()
                .filter(teacher -> teacher.getId().equals(teacherId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(teacherId.toString()));
    }

    private void throwIfTeacherAssigned(Subject subject) {
        if (subjectTeachers.containsKey(subject)) {
            throw new SubjectHasAssignedTeacherException(subject.getId().toString());
        }
    }


}
