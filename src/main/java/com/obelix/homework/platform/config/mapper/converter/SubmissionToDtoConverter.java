package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.GradeDto;
import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentStudentDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.Submission;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class SubmissionToDtoConverter implements Converter<Submission, SubmissionDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SubmissionDto convert(MappingContext<Submission, SubmissionDto> mappingContext) {
        var source = mappingContext.getSource();
        return SubmissionDto.builder()
                .id(source.getId())
                .solution(source.getSolution())
                .timestamp(source.getTimestamp())
                .teacherComment(source.getTeacherComment())
                .grade(modelMapper.map(source.getGrade(), GradeDto.class))
                .homeworkAssignment(modelMapper.map(source.getHomeworkAssignment(), HomeworkAssignmentStudentDto.class))
                .build();

    }
}
