package com.obelix.homework.platform.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
}
