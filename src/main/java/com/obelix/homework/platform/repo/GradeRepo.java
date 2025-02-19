package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GradeRepo extends JpaRepository<Grade, UUID> {
}
