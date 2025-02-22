package com.obelix.homework.platform.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
}
