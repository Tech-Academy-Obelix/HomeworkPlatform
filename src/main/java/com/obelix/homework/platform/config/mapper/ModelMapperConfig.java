package com.obelix.homework.platform.config.mapper;

import com.obelix.homework.platform.config.mapper.converter.DtoToHomeworkAssignmentConverter;
import com.obelix.homework.platform.config.mapper.converter.SubjectToDtoConverter;
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
    @Bean
    public ModelMapper modelMapper() {
        modelMapper.addConverter(dtoToHomeworkAssignmentConverter);
        modelMapper.addConverter(subjectToDtoConverter);
        return modelMapper;
    }
}