package com.lxp.infrastructure.row;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import java.sql.Timestamp;

public record UserRow(
        Long id,
        String name,
        String password,
        String nickname,
        ActiveStatus activeStatus,
        UserRole role,
        Timestamp createdAt,
        Timestamp updatedAt) {}
