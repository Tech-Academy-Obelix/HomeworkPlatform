package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.model.domain.entity.SubmittedHomeworkAssignment;
import com.obelix.homework.platform.repo.domain.SubmittedHomeworkAssignmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlagiarismDetectionService {
    private final SubmittedHomeworkAssignmentRepo submittedHomeworkAssignmentRepo;
    public double checkPlagiarism(SubmittedHomeworkAssignment submittedAssignment) {
        var newSubmission = submittedAssignment.getSolution();
        if (newSubmission == null || newSubmission.trim().isEmpty()) {
            return 0.0;
        }

        double maxSimilarity = 0.0;
        for (String existing : existingSubmissions(submittedAssignment.getId())) {
            double similarity = calculateSimilarity(newSubmission, existing);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
            }
        }

        return maxSimilarity;
    }

    private List<String> existingSubmissions(UUID assignmentId) {
        return submittedHomeworkAssignmentRepo.findAll().stream()
                .filter(s -> !s.getId().equals(assignmentId))
                .map(SubmittedHomeworkAssignment::getSolution)
                .collect(Collectors.toList());
    }

    private double calculateSimilarity(String text1, String text2) {
        text1 = text1.toLowerCase().replaceAll("[^a-zA-Zа-яА-Я]", "");
        text2 = text2.toLowerCase().replaceAll("[^a-zA-Zа-яА-Я]", "");

        int commonChars = 0;
        int minLength = Math.min(text1.length(), text2.length());

        for (int i = 0; i < minLength; i++) {
            if (text1.charAt(i) == text2.charAt(i)) {
                commonChars++;
            }
        }

        return (double) commonChars / Math.max(text1.length(), text2.length());
    }
}

