package com.obelix.homework.platform.repo;

import com.obelix.homework.platform.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepo extends JpaRepository<Submission, UUID> {
    List<Submission> findByStudentUsername(String studentUsername);
}
