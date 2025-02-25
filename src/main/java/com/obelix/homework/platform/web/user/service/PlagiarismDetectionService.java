package com.obelix.homework.platform.web.user.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlagiarismDetectionService {

    public double checkPlagiarism(String newSubmission, List<String> existingSubmissions) {
        if (newSubmission == null || newSubmission.trim().isEmpty()) {
            return 0.0;
        }

        double maxSimilarity = 0.0;
        for (String existing : existingSubmissions) {
            double similarity = calculateSimilarity(newSubmission, existing);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
            }
        }


        return maxSimilarity;
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

