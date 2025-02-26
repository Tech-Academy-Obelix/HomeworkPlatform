package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.CourseDto;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectInCourseDto;
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
public class CourseToManagementDtoConverter implements Converter<Course, CourseDto> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CourseDto convert(MappingContext<Course, CourseDto> mappingContext) {
        var source = mappingContext.getSource();
        return new CourseDto(
                source.getId(),
                source.getName(),
                getSubjectDtos(source.getSubjects(), source),
                getStudentDtos(source.getStudents()),
                null);
    }

    private List<SubjectInCourseDto> getSubjectDtos(List<Subject> subjects, Course course) {
        if (subjects == null || subjects.isEmpty()) return null;
        return subjects.stream().map(subject -> getSubjectDto(subject, course)).collect(Collectors.toList());
    }

    private SubjectInCourseDto getSubjectDto(Subject subject, Course course) {
        var subjectDto = modelMapper.map(subject, SubjectInCourseDto.class);
        var teacher = course.getSubjectTeachers().get(subject);
        if (teacher != null) {
            subjectDto.setTeacher(modelMapper.map(teacher, UserDto.class));
        }
        return subjectDto;
    }

    private List<UserDto> getStudentDtos(List<Student> students) {
        if (students == null || students.isEmpty()) return null;
        return students.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @PostConstruct
    private void init() {
        modelMapper.addConverter(new SubjectToDtoConverter());
    }
}