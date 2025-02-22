package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepo extends JpaRepository<Subject, UUID> {
    Subject getSubjectById(UUID id);

    void deleteSubjectById(UUID id);
}
