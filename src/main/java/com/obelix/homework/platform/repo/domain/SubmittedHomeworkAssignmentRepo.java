package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.model.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmittedHomeworkAssignmentRepo extends JpaRepository<SubmittedHomeworkAssignment, UUID> {
}
