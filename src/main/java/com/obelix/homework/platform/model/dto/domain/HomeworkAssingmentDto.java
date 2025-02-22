package com.obelix.homework.platform.model.dto.domain;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;
@Getter
public class HomeworkAssingmentDto {
    private String assignmentName;
    private String assignmentDescription;

    private Date dueDate;
    private UUID subjectId;

}
