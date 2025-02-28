package com.obelix.homework.platform.config.mapper;

import com.obelix.homework.platform.config.mapper.converter.*;
import com.obelix.homework.platform.config.mapper.converter.CourseToManagementDtoConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor

public class ModelMapperConfig {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final DtoToHomeworkAssignmentConverter dtoToHomeworkAssignmentConverter;
    private final SubjectToDtoConverter subjectToDtoConverter;
    private final CourseToManagementDtoConverter courseToManagementDtoConverter;
    private final StudentToDtoConverter studentToDtoConverter;
    private final TeacherToDtoConverter teacherToDtoConverter;
    private final SubmissionToTeacherDtoConverter submissionToTeacherDtoConverter;
    @Bean
    public ModelMapper modelMapper() {
        modelMapper.addConverter(dtoToHomeworkAssignmentConverter);
        modelMapper.addConverter(subjectToDtoConverter);
        modelMapper.addConverter(courseToManagementDtoConverter);
        modelMapper.addConverter(subjectToDtoConverter);
        modelMapper.addConverter(studentToDtoConverter);
        modelMapper.addConverter(teacherToDtoConverter);
        modelMapper.addConverter(submissionToTeacherDtoConverter);
        return modelMapper;
    }
}