package com.obelix.homework.platform.model.domain.dto.subject;

import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SubjectInTeacherDto extends SubjectDto {
    private List<HomeworkAssignmentResponseDto> assignments;

    public SubjectInTeacherDto(UUID id, String name) {
        super(id, name);
    }
}
