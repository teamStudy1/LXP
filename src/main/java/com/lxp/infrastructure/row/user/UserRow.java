package com.lxp.infrastructure.row.user;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import java.sql.Timestamp;

public record UserRow(
        Long id,
        String email,
        String password,
        String name,
        ActiveStatus activeStatus,
        UserRole role,
        UserProfileRow userProfile,
        Timestamp createdAt,
        Timestamp updatedAt) {}
