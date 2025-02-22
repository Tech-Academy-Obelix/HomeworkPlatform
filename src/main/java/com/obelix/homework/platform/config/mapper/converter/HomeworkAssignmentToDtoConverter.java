package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentResponseDto;
import com.obelix.homework.platform.model.domain.dto.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class HomeworkAssignmentToDtoConverter implements Converter<HomeworkAssignment, HomeworkAssignmentResponseDto> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public HomeworkAssignmentResponseDto convert(MappingContext<HomeworkAssignment, HomeworkAssignmentResponseDto> mappingContext) {
        var source = mappingContext.getSource();
        return HomeworkAssignmentResponseDto.builder()
                .assignmentId(source.getId())
                .assignmentName(source.getAssignmentName())
                .assignmentDescription(source.getAssignmentDescription())
                .subject(modelMapper.map(source.getSubject(), SubjectDto.class))
                .assignmentDate(source.getAssignmentDate())
                .dueDate(source.getDueDate())
                .build();
    }

    @PostConstruct
    protected void init() {
        modelMapper.addConverter(new HomeworkAssignmentToDtoConverter());
    }
}
