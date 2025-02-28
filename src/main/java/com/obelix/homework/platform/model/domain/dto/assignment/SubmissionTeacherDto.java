package com.obelix.homework.platform.model.domain.dto.assignment;

import com.obelix.homework.platform.model.user.dto.StudentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmissionTeacherDto extends SubmissionDto {
    private StudentDto student;
}
