package com.obelix.homework.platform.model.domain.dto;

import com.obelix.homework.platform.model.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SubjectInCourseDto {
    private UUID id;
    private String name;
    private UserDto teacher;
}
