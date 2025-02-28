package com.obelix.homework.platform.web.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obelix.homework.platform.model.domain.dto.GradeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AIGradingService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public GradeDto gradeSubmissionWithAI(String submissionText) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                Map.of("role", "system", "content", "You are a strict teacher. Grade this assignment from 2 to 6 and provide short feedback."),
                Map.of("role", "user", "content", submissionText)
        });
        requestBody.put("max_tokens", 100);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        return extractGradeFromResponse(response.getBody());
    }

    private GradeDto extractGradeFromResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            String aiResponse = rootNode.path("choices").get(0).path("message").path("content").asText();

            String[] responseParts = aiResponse.split(" - ", 2);
            Double grade = parseGrade(responseParts[0]);
            String comment = responseParts.length > 1 ? responseParts[1] : "No comment.";

            GradeDto gradeDto = new GradeDto();
            gradeDto.setGrade(grade);
            gradeDto.setTeacherComment(comment);

            return gradeDto;

        } catch (Exception e) {
            return null;
        }
    }

    private Double parseGrade(String gradeText) {
        try {
            return Double.parseDouble(gradeText.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            return 2.0;
        }
    }
}
