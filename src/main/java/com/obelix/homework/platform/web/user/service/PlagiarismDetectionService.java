package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.model.domain.dto.assignment.SubmissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlagiarismDetectionService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public double checkPlagiarism(SubmissionDto submittedAssignment) {
        String submissionText = submittedAssignment.getSolution();
        if (submissionText == null || submissionText.trim().isEmpty()) {
            return 0.0;
        }

        String aiResponse = checkWithAI(submissionText);
        return extractSimilarityScore(aiResponse);
    }

    private String checkWithAI(String submissionText) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                Map.of("role", "system", "content", "You are an AI that detects plagiarism. Compare this text to a large academic database and return a plagiarism percentage."),
                Map.of("role", "user", "content", submissionText)
        });
        requestBody.put("max_tokens", 50);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

    private double extractSimilarityScore(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            String aiResponse = rootNode.path("choices").get(0).path("message").path("content").asText();

            return aiResponse.matches(".*\\d+.*") ? Double.parseDouble(aiResponse.replaceAll("[^0-9]", "")) / 100.0 : 0.0;

        } catch (Exception e) {
            return 0.0;
        }
    }
}