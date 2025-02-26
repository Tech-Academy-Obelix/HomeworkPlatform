package com.obelix.homework.platform.model.domain.dto.subject;

import com.obelix.homework.platform.model.user.dto.UserDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SubjectManagementDto extends SubjectDto {
    private List<UserDto> teachers;

    public SubjectManagementDto(UUID id, String name, List<UserDto> teachers) {
        super(id, name);
        this.teachers = teachers;
    }
}
