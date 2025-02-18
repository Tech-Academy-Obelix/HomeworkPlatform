package com.obelix.homework.platform.service;

import com.obelix.homework.platform.model.Submission;
import com.obelix.homework.platform.repo.SubmissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepo submissionRepo;

    public Submission submitAssignment(Submission submission) {
        submission.setStatus("PENDING");
        return submissionRepo.save(submission);
    }

    public List<Submission> submitBulkAssignments(List<Submission> submissions) {
        submissions.forEach(submission -> submission.setStatus("PENDING"));
        return submissionRepo.saveAll(submissions);
    }

    public List<Submission> getSubmissionsByStudent(String username) {
        return submissionRepo.findByStudentUsername(username);
    }

    public List<Submission> getAllSubmissions() {
        return submissionRepo.findAll();
    }
}
