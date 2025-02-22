package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.HomeworkAssignmentCreateDto;
import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import com.obelix.homework.platform.repo.domain.SubjectRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DtoToHomeworkAssignmentConverter implements Converter<HomeworkAssignmentCreateDto, HomeworkAssignment> {
    private final SubjectRepo subjectRepo;

    @Override
    public HomeworkAssignment convert(MappingContext<HomeworkAssignmentCreateDto, HomeworkAssignment> mappingContext) {
        var source = mappingContext.getSource();
        return HomeworkAssignment.builder()
                .assignmentName(source.getAssignmentName())
                .assignmentDescription(source.getAssignmentDescription())
                .assignmentDate(new Date())
                .dueDate(source.getDueDate())
                .subject(subjectRepo.getSubjectById(source.getSubjectId()))
                .build();

    }
}
