package com.obelix.homework.platform.model.entity.domain;

import com.obelix.homework.platform.model.entity.user.Student;
import com.obelix.homework.platform.model.entity.user.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Min(2)
    @Max(6)
    private double grade;

    Date timestamp;

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;


}
