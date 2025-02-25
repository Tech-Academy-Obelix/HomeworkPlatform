package com.obelix.homework.platform.model.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class HomeworkAssignmentCreateDto {
    private String assignmentName;
    private String assignmentDescription;

    private Date dueDate;
    private UUID subjectId;


}
