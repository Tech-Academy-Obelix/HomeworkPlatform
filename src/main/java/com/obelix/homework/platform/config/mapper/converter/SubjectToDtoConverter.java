package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.dto.domain.SubjectDto;
import com.obelix.homework.platform.model.dto.user.UserDto;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.model.entity.user.Teacher;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubjectToDtoConverter implements Converter<Subject, SubjectDto> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SubjectDto convert(MappingContext<Subject, SubjectDto> mappingContext) {
        var source = mappingContext.getSource();
        return SubjectDto.builder()
                .id(source.getId())
                .name(source.getSubjectName())
                .teachers(getTeacherDtos(source.getTeachers()))
                .build();
    }

    private List<UserDto> getTeacherDtos(List<Teacher> teachers) {
        return teachers.stream()
                .map(teacher -> modelMapper.map(teacher, UserDto.class))
                .collect(Collectors.toList());
    }
}
