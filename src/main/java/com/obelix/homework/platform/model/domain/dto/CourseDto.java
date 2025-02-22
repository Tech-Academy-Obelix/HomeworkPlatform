package com.obelix.homework.platform.model.domain.dto;

import com.obelix.homework.platform.model.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CourseDto {
    private UUID id;
    private String name;
    private List<SubjectDto> subjects;
    private List<UserDto> students;
    private List<HomeworkAssignmentCreateDto> assignments;
}
