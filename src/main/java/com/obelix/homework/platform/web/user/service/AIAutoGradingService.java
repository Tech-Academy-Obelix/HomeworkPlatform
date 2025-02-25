package com.obelix.homework.platform.web.user.service;

import org.springframework.stereotype.Service;

@Service
public class AIAutoGradingService {

    public String gradeSubmission(String submissionText) {
        if (submissionText == null || submissionText.trim().isEmpty()) {
            return "Слаб 2";
        }

        int wordCount = submissionText.split("\\s+").length;

        double score = calculateScore(submissionText, wordCount);
        return mapScoreToBulgarianGrade(score);
    }


    private double calculateScore(String text, int wordCount) {
        double baseScore = Math.min(wordCount / 100.0, 6.0);
        return Math.max(2.0, baseScore);
    }

    private String mapScoreToBulgarianGrade(double score) {
        if (score >= 5.5) return "Отличен 6";
        if (score >= 4.5) return "Много добър 5";
        if (score >= 3.5) return "Добър 4";
        if (score >= 2.5) return "Среден 3";
        return "Слаб 2";
    }
}
