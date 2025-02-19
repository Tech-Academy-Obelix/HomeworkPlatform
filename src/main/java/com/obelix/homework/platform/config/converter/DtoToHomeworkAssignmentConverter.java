package com.obelix.homework.platform.config.converter;


import com.obelix.homework.platform.model.dto.HomeworkAssingmentDto;
import com.obelix.homework.platform.model.entity.domain.HomeworkAssignment;
import com.obelix.homework.platform.repo.SubjectRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DtoToHomeworkAssignmentConverter implements Converter<HomeworkAssingmentDto, HomeworkAssignment> {
    private final SubjectRepo subjectRepo;

    @Override
    public HomeworkAssignment convert(MappingContext<HomeworkAssingmentDto, HomeworkAssignment> mappingContext) {
        HomeworkAssingmentDto source = mappingContext.getSource();
        return HomeworkAssignment.builder()
                .assignmentName(source.getAssignmentName())
                .assignmentDescription(source.getAssignmentDescription())
                .assignmentDate(new Date())
                .dueDate(source.getDueDate())
                .subject(subjectRepo.getSubjectById(source.getSubjectId()))
                .build();

    }
}
