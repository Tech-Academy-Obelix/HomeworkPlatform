package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.CourseManagementDto;
import com.obelix.homework.platform.model.user.dto.StudentDto;
import com.obelix.homework.platform.model.user.entity.Student;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class StudentToDtoConverter implements Converter<Student, StudentDto> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public StudentDto convert(MappingContext<Student, StudentDto> mappingContext) {
        var source = mappingContext.getSource();
        var dto = new StudentDto(source);
        var course = source.getCourse();
        if (course != null) {
            dto.setCourse(modelMapper.map(course, CourseManagementDto.class));
        }
        return dto;
    }
}
