package com.obelix.homework.platform.model.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class HomeworkAssignmentCreateDto {
    private String name;
    private String description;
    private Date dueDate;
}
