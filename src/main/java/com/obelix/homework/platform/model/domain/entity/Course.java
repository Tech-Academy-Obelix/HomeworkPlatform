package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.config.exception.*;
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
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Student> students;

    @ManyToMany(fetch = FetchType.EAGER)
    private Map<Subject, Teacher> subjectTeachers;

    @ManyToMany(fetch = FetchType.EAGER)
    private Map<HomeworkAssignment, Subject> assignmentSubjects;

    @ManyToMany(fetch = FetchType.EAGER)
    private Map<Student, Submission> studentSubmissions;

    public Course(String name) {
        this.name = name;
    }

    public Double getAverageGrade() {
        double sum = 0;
        for (Student student : students) {
            var grade = student.getAverageGrade();
            sum += grade == null ? 0 : grade;
        }
        return sum / students.size();
    }

    public void addSubmission(UUID studentId, Submission submission) {
        var student = getStudentById(studentId);
        if (!studentSubmissions.containsKey(student)) {
            student.addSubmission(submission);
            studentSubmissions.put(student, submission);
        }  else {
            throw new ResubmitionException(submission.getHomeworkAssignment().id.toString());
        }
    }

    public List<Submission> getSubmittedHomeworkAssignmentsBySubjectId(UUID subjectId) {
        return students.stream()
                .flatMap(student -> student.getSubmissions().stream()  // Flatten the stream
                        .filter(submission -> assignmentSubjects.get(submission.getHomeworkAssignment()).getId().equals(subjectId)))
                .collect(Collectors.toList());  // Collect into a single list of submissions
    }

    public Student getStudentBySubmissionId(UUID submissionId) {
        return studentSubmissions.entrySet().stream().filter(entry -> entry.getValue().getId().equals(submissionId))
                .findFirst()
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId.toString()))
                .getKey();
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
        teacher.getCourses().add(this);
        subjectTeachers.put(subject, teacher);
        subject.addTeacher(teacher);
    }

    public void removeTeacherFromSubject(UUID teacherId, UUID subjectId) {
        var subject = getSubjectById(subjectId);
        var teacher = getTeacherById(teacherId);
        subjectTeachers.remove(subject);
        subject.removeTeacher(teacher);
        if (!subjectTeachers.containsValue(teacher)) {
            teacher.getCourses().remove(this);
        }
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

    public HomeworkAssignment getAssignmentById(UUID assignmentId) {
        return assignmentSubjects.keySet().stream()
                .filter(assignment -> assignment.getId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId.toString()));
    }

    public void addAssignmentToSubject(HomeworkAssignment assignment, UUID subjectId) {
        assignmentSubjects.put(assignment, getSubjectById(subjectId));
    }

    public void removeAssignmentById(UUID assignmentId) {
        assignmentSubjects.remove(getAssignmentById(assignmentId));
    }

    public List<HomeworkAssignment> getAssignmentsInSubject(UUID subjectId) {
        return assignmentSubjects.entrySet().stream()
                .filter(entry -> entry.getValue().getId().equals(subjectId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<HomeworkAssignment> getAssignments() {
        return assignmentSubjects.keySet().stream().toList();
    }

    public Subject getSubjectByAssignment(HomeworkAssignment assignment) {
        return assignmentSubjects.get(assignment);
    }

    public Submission getSubmissionById(UUID submissionId) {
        return studentSubmissions.values().stream().filter(submission -> submission.getId().equals(submissionId))
                .findFirst()
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId.toString()));
    }
}
