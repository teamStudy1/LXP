package com.lxp.user.domain.model.enums;

public enum UserRole {
    STUDENT,
    INSTRUCTOR,
    ADMIN;

    public static boolean isValid(String input) {
        if (input == null) return false;

        try {
            UserRole.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
