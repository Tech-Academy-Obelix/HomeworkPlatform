package com.obelix.homework.platform.model.domain.dto.assignment;

import com.obelix.homework.platform.model.domain.dto.GradeDto;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SubmissionDto {
    private UUID id;

    private String solution;
    private Date timestamp;

    private String teacherComment;

    private HomeworkAssignmentStudentDto homeworkAssignment;

    private GradeDto grade;
}
