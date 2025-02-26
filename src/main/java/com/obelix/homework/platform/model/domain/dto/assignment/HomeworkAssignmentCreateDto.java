package com.obelix.homework.platform.model.domain.dto.assignment;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class HomeworkAssignmentCreateDto {
    private String name;
    private String description;
    private Date dueDate;
}
