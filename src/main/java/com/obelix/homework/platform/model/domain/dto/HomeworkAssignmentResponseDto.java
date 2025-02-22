package com.obelix.homework.platform.model.domain.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public class HomeworkAssignmentResponseDto {
    private UUID assignmentId;
    private String assignmentName;
    private String assignmentDescription;
    private SubjectDto subject;
    private Date assignmentDate;
    private Date dueDate;

}
