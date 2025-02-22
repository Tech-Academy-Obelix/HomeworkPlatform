package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.SubjectDto;
import com.obelix.homework.platform.model.domain.entity.Course;
import com.obelix.homework.platform.model.domain.entity.Subject;
import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.model.user.entity.Student;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseToDtoConverter implements Converter<Course, CourseDto> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CourseDto convert(MappingContext<Course, CourseDto> mappingContext) {
        var source = mappingContext.getSource();
        return CourseDto.builder()
                .id(source.getId())
                .name(source.getCourseName())
                .subjects(getSubjectDtos(source.getSubjects()))
                .students(getStudentDtos(source.getStudents()))
                .build();
    }

    private List<SubjectDto> getSubjectDtos(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) return null;
        return subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());
    }

    private List<UserDto> getStudentDtos(List<Student> students) {
        if (students == null || students.isEmpty()) return null;
        return students.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @PostConstruct
    protected void init() {
        modelMapper.addConverter(new SubjectToDtoConverter());
    }
}
