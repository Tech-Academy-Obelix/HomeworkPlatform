package com.obelix.homework.platform.model.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RegisterDto {
    @NotBlank
    @NotNull(message = "Username cannot be null")
    String username;

    @NotBlank
    @NotNull(message = "First name cannot be null")
    String firstName;

    @NotBlank
    @NotNull(message = "Last name cannot be null")
    String lastName;

    @NotNull(message = "Invitation code cannot be null")
    UUID inviteCode;
}
