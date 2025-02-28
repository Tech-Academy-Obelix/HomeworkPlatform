package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentResponseDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectInTeacherDto;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.model.domain.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubjectToDtoInTeacherConverter implements Converter<Subject, SubjectInTeacherDto> {
    private final ModelMapper modelMapper;

    @Override
    public SubjectInTeacherDto convert(MappingContext<Subject, SubjectInTeacherDto> mappingContext) {
        var source = mappingContext.getSource();
        return new SubjectInTeacherDto(source.getId(), source.getSubjectName());
    }

    private List<HomeworkAssignmentResponseDto> getAssignmentDtos(List<HomeworkAssignment> assignments) {
        return assignments.stream()
                .map(assignmentDto -> modelMapper.map(assignmentDto, HomeworkAssignmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
