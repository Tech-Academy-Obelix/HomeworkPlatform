package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubmittedHomeworkAssignmentRepo extends JpaRepository<Submission, UUID> {
}
