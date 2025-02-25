package com.obelix.homework.platform.model.user.dto;

import com.obelix.homework.platform.model.domain.dto.CourseManagementDto;
import com.obelix.homework.platform.model.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDto extends UserDto {
    private List<CourseManagementDto> courses;
    public TeacherDto(User user) {
        super(user);
    }
}
