package com.obelix.homework.platform.model.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class SubmittedHomeworkAssignmentDto {
    private String solution;
    private Date timestamp;
}
