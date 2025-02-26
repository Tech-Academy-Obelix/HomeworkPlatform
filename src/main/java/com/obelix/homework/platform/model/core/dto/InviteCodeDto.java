package com.obelix.homework.platform.model.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InviteCodeDto {
    @NotBlank
    @NotNull(message = "Role cannot be null")
    String role;

    @NotBlank
    @Email(message = "Invalid email")
    String email;
}
