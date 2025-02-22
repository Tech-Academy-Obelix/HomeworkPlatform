package com.obelix.homework.platform.model.dto.domain;

import com.obelix.homework.platform.model.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class SubjectDto {
    private UUID id;
    private String name;
    private List<UserDto> teachers;
}
