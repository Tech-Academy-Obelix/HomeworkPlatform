package com.obelix.homework.platform.config.mapper.converter;

import com.obelix.homework.platform.model.domain.dto.subject.SubjectManagementDto;
import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.model.domain.entity.Subject;
import com.obelix.homework.platform.model.user.entity.Teacher;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubjectToDtoConverter implements Converter<Subject, SubjectManagementDto> {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SubjectManagementDto convert(MappingContext<Subject, SubjectManagementDto> mappingContext) {
        var source = mappingContext.getSource();
        return new SubjectManagementDto(source.getId(), source.getSubjectName(), null, getTeacherDtos(source.getTeachers()));
    }

    private List<UserDto> getTeacherDtos(List<Teacher> teachers) {
        if (teachers == null || teachers.isEmpty()) return null;
        return teachers.stream()
                .map(teacher -> modelMapper.map(teacher, UserDto.class))
                .collect(Collectors.toList());
    }
}
