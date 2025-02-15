package com.obelix.homework.platform.role;

import com.obelix.homework.platform.exception.NoSuchRoleException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER"),
    ADMIN("ROLE_ADMIN"),
    OWNER("ROLE_OWNER");

    private final String roleAsString;

    @Override
    public String toString() {
        return roleAsString;
    }

    public static Role fromString(String roleAsString) {
        return switch (roleAsString) {
            case "student" -> STUDENT;
            case "teacher" -> TEACHER;
            case "admin" -> ADMIN;
            case null, default -> throw new NoSuchRoleException(roleAsString);
        };
    }
}
