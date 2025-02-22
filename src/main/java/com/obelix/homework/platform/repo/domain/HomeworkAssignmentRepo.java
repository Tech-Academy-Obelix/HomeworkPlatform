package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.HomeworkAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HomeworkAssignmentRepo extends JpaRepository<HomeworkAssignment, UUID> {
    HomeworkAssignment getHomeworkAssignmentById(UUID id);
}
