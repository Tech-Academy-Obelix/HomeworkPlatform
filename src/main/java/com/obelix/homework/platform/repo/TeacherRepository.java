package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.entity.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
