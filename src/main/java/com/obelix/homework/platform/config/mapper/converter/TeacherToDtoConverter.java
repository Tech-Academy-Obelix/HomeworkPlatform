package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.user.dto.TeacherDto;
import com.obelix.homework.platform.model.user.entity.Teacher;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherToDtoConverter implements Converter<Teacher, TeacherDto> {
    private final static ModelMapper modelMapper = new ModelMapper();
    @Override
    public TeacherDto convert(MappingContext<Teacher, TeacherDto> mappingContext) {
        var source = mappingContext.getSource();
        var dto = new TeacherDto(source);
        var courses = source.getCourses();
        if (courses != null) {
            dto.setCourses(getCourseDtos(source.getCourses()));
        }
        return dto;
    }

    private List<CourseDto> getCourseDtos(List<Course> courses) {
        if (courses == null) return null;
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }
}
