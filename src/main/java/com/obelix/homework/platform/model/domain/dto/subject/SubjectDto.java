package com.obelix.homework.platform.model.domain.dto.subject;

import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentResponseDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private UUID id;
    private String name;
    private List<HomeworkAssignmentResponseDto> assignments;
}
