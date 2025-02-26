package com.obelix.homework.platform.model.domain.entity;

import com.obelix.homework.platform.model.user.entity.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String solution;
    private Date timestamp;

    private String teacherComment;

    @ManyToOne
    private HomeworkAssignment homeworkAssignment;

    @OneToOne
    private Grade grade;

    @OneToOne
    private Student student;

    public Submission(String solution, HomeworkAssignment homeworkAssignment) {
        this.solution = solution;
        this.timestamp = new Date();
        this.homeworkAssignment = homeworkAssignment;
    }
}
