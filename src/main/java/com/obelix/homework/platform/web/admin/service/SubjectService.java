package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.SubjectNotFoundException;
import com.obelix.homework.platform.model.domain.dto.subject.SubjectManagementDto;
import com.obelix.homework.platform.model.domain.entity.Subject;
import com.obelix.homework.platform.repo.domain.SubjectRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final ModelMapper modelMapper;

    public List<SubjectManagementDto> getAllSubjects() {
        return subjectRepo.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectManagementDto.class))
                .collect(Collectors.toList());
    }

    public SubjectManagementDto getSubjectById(UUID id) {
        return modelMapper.map(
                subjectRepo.findById(id).orElseThrow(() -> new SubjectNotFoundException(id.toString())),
                SubjectManagementDto.class);
    }

    public SubjectManagementDto createSubject(String name){
        return modelMapper.map(subjectRepo.save(new Subject(name)), SubjectManagementDto.class);
    }

    public void deleteSubjectById(UUID id) {
        subjectRepo.deleteById(id);
    }
}
