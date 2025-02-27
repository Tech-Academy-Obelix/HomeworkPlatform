package com.obelix.homework.platform.model.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class GradeDto {
    private UUID id;
    private double grade;
    private String teacherComment;
}
