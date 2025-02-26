package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.assignment.HomeworkAssignmentCreateDto;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DtoToHomeworkAssignmentConverter implements Converter<HomeworkAssignmentCreateDto, HomeworkAssignment> {
    @Override
    public HomeworkAssignment convert(MappingContext<HomeworkAssignmentCreateDto, HomeworkAssignment> mappingContext) {
        var source = mappingContext.getSource();
        return HomeworkAssignment.builder()
                .name(source.getName())
                .description(source.getDescription())
                .assignmentDate(new Date())
                .dueDate(source.getDueDate())
                //.subject(subjectRepo.getSubjectById(source.getSubjectId()))
                .build();

    }
}
