package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Teacher getTeacherById(UUID teacherId);
}
