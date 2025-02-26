package com.obelix.homework.platform.model.domain.dto.assignment;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class HomeworkAssignmentResponseDto extends HomeworkAssignmentCreateDto{
    private UUID id;
    private Date assignmentDate;
}
