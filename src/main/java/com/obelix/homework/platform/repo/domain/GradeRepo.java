package com.obelix.homework.platform.repo.domain;

import com.obelix.homework.platform.model.domain.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GradeRepo extends JpaRepository<Grade, UUID> {

}
