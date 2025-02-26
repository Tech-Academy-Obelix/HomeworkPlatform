package com.obelix.homework.platform.model.domain.dto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectInCourseDto;
import com.obelix.homework.platform.model.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private UUID id;
    private String name;
    private List<SubjectInCourseDto> subjects;
    private List<UserDto> students;
    private List<HomeworkAssignmentResponseDto> assignments;
}
