package com.lxp.user.persistance.row;

import com.lxp.user.domain.model.enums.ActiveStatus;
import com.lxp.user.domain.model.enums.UserRole;
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
