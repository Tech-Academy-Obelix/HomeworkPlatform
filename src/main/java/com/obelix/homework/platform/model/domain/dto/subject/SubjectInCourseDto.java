package com.obelix.homework.platform.model.domain.dto.subject;

import com.obelix.homework.platform.model.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SubjectInCourseDto extends SubjectDto {
    private UserDto teacher;
}
