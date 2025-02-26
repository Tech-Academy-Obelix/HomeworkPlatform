package com.obelix.homework.platform.repo.user;

import com.obelix.homework.platform.model.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepo extends JpaRepository<Student, UUID> {
}
