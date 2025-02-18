package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.HomeworkAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HomeworkAssignmentRepo extends JpaRepository<HomeworkAssignment, UUID> {
}
