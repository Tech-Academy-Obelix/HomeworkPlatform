package com.obelix.homework.platform.model.domain.dto.assignment;

import com.obelix.homework.platform.model.domain.entity.Submission;
import com.obelix.homework.platform.model.user.dto.StudentDto;
import com.obelix.homework.platform.model.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmissionTeacherDto extends SubmissionDto {
    private UserDto student;

    public SubmissionTeacherDto(SubmissionDto submissionDto) {
        super(submissionDto);
    }
}
