package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.model.entity.domain.Subject;
import com.obelix.homework.platform.repo.SubjectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepo subjectRepo;

    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    public Subject getSubjectById(UUID id) {
        return subjectRepo.getSubjectById(id);
    }

    public Subject createSubject(String name){
        return subjectRepo.save(new Subject(name));
    }

    public void deleteSubjectById(UUID id) {
        subjectRepo.deleteSubjectById(id);
    }
}
