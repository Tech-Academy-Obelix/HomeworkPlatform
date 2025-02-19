package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepo extends JpaRepository<Subject, UUID> {
    Subject getSubjectById(UUID id);
}
