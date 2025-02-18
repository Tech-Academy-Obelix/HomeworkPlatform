package com.obelix.homework.platform.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
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
