package com.obelix.homework.platform.model.domain.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class HomeworkAssignmentResponseDto {
    private UUID id;
    private String name;
    private String description;
    private Date assignmentDate;
    private Date dueDate;

}
