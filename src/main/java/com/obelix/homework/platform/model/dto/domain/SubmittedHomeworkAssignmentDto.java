package com.obelix.homework.platform.model.dto.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SubmittedHomeworkAssignmentDto {
    private UUID id;
    private String solution;
}
