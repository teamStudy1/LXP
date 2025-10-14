package com.lxp.infrastructure.row;

import com.lxp.domain.user.UserRole;
import java.sql.Timestamp;

public record UserRow(
        String name,
        String password,
        String nickname,
        boolean isActive,
        UserRole role,
        Timestamp createdAt,
        Timestamp updatedAt) {}
