package com.obelix.homework.platform.model.domain.dto.subject;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private UUID id;
    private String name;
}
