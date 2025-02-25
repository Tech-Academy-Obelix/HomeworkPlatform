package com.obelix.homework.platform.web.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIAutoGradingService {

    private final AIGradingAPIService aiGradingAPIService;

    public double gradeSubmission(String submissionText) {
        if (submissionText == null || submissionText.trim().isEmpty()) {
            return 2.00;
        }

        String aiGrade = aiGradingAPIService.gradeSubmissionWithAI(submissionText);
        double numericGrade = parseBulgarianGrade(aiGrade);

        if (numericGrade != 0.0) {
            return numericGrade;
        }

        int wordCount = submissionText.split("\\s+").length;
        double score = calculateScore(wordCount);
        return mapScoreToBulgarianGrade(score);
    }

    private double calculateScore(int wordCount) {
        return Math.max(2.0, Math.min(wordCount / 100.0, 6.0));
    }

    private double mapScoreToBulgarianGrade(double score) {
        if (score >= 5.5) return 6.00;
        if (score >= 4.5) return 5.00;
        if (score >= 3.5) return 4.00;
        if (score >= 2.5) return 3.00;
        return 2.00;
    }

    private double parseBulgarianGrade(String aiGrade) {
        if (aiGrade == null) return 0.0;

        if (aiGrade.contains("6")) return 6.00;
        if (aiGrade.contains("5")) return 5.00;
        if (aiGrade.contains("4")) return 4.00;
        if (aiGrade.contains("3")) return 3.00;
        return 2.00;
    }
}
