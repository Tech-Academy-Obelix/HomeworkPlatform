package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.model.dto.domain.SubjectDto;
import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.repo.domain.SubjectRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final ModelMapper modelMapper;

    public List<SubjectDto> getAllSubjects() {
        return subjectRepo.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());
    }

    public SubjectDto getSubjectById(UUID id) {
        return modelMapper.map(subjectRepo.getSubjectById(id), SubjectDto.class);
    }

    public SubjectDto createSubject(String name){
        return modelMapper.map(subjectRepo.save(new Subject(name)), SubjectDto.class);
    }

    public void deleteSubjectById(UUID id) {
        subjectRepo.deleteSubjectById(id);
    }
}
