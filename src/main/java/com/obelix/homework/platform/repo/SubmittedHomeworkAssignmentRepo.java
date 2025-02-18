package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.SubmittedHomeworkAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubmittedHomeworkAssignmentRepo extends JpaRepository<SubmittedHomeworkAssignment, UUID> {
}
