package com.obelix.homework.platform.model.domain.dto.assignment;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SubmissionDto {
    private UUID id;
    private String solution;
}
