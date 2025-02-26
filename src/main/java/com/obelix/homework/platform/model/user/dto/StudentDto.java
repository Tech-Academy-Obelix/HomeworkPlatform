package com.obelix.homework.platform.model.user.dto;

import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto extends UserDto {
    private CourseDto course;
    public StudentDto(User user) {
        super(user);
    }

}
