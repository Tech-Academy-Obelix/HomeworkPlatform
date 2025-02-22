package com.obelix.homework.platform.model.dto.core;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RegisterDto {
    String username;
    String firstName;
    String lastName;
    UUID inviteCode;
}
