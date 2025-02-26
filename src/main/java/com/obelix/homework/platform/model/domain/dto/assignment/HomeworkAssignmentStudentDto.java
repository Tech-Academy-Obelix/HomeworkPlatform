package com.obelix.homework.platform.model.domain.dto.assignment;

import com.obelix.homework.platform.model.domain.dto.subject.SubjectDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HomeworkAssignmentStudentDto extends HomeworkAssignmentResponseDto {
    private SubjectDto subjectDto;
}
