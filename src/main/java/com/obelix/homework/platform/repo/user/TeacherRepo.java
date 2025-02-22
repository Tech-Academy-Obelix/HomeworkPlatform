package com.obelix.homework.platform.repo.user;

import com.obelix.homework.platform.model.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepo extends JpaRepository<Teacher, UUID> {
}
