package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionTeacherDto;
import com.obelix.homework.platform.model.domain.entity.Submission;
import com.obelix.homework.platform.model.user.dto.StudentDto;
import com.obelix.homework.platform.model.user.dto.UserDto;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class SubmissionToTeacherDtoConverter implements Converter<Submission, SubmissionTeacherDto> {
    public static ModelMapper modelMapper = new ModelMapper();
    @Override
    public SubmissionTeacherDto convert(MappingContext<Submission, SubmissionTeacherDto> mappingContext) {
        var source = mappingContext.getSource();
        var dto = new SubmissionTeacherDto(modelMapper.map(source, SubmissionDto.class));
        dto.setStudent(modelMapper.map(source.getStudent(), UserDto.class));
        return dto;
    }

    @PostConstruct
    public void init() {
        modelMapper.addConverter(new StudentToDtoConverter());
    }
}
