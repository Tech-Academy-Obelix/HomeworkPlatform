package com.obelix.homework.platform.model.user.dto;

import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.model.user.entity.User;
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
    private String email;
    private Role role;

    public UserDto(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
    }
}
