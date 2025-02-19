package com.obelix.homework.platform.config.security.role;

import com.obelix.homework.platform.config.exception.NoSuchRoleException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_OWNER("ROLE_OWNER");

    private final String roleAsString;

    @Override
    public String toString() {
        return roleAsString;
    }

    public static Role fromString(String roleAsString) {
        return switch (roleAsString) {
            case "student" -> ROLE_STUDENT;
            case "teacher" -> ROLE_TEACHER;
            case "admin" -> ROLE_ADMIN;
            case null, default -> throw new NoSuchRoleException(roleAsString);
        };
    }

    public static String toSimpleString(Role role) {
        return switch (role) {
            case ROLE_STUDENT -> "student";
            case ROLE_TEACHER -> "teacher";
            case ROLE_ADMIN -> "admin";
            case null, default -> throw new NoSuchRoleException("null");

        };
    }
}
