package com.obelix.homework.platform.model.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserDto {
    String username;
    String firstName;
    String lastName;
    UUID inviteCode;
}
