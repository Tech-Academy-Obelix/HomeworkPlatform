package com.obelix.homework.platform.model.domain.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GradeDto {
    private UUID id;
    private double grade;
    private String teacherComment;

}
