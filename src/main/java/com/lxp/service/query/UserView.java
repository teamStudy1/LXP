package com.lxp.service.query;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;

import java.sql.Timestamp;

public record UserView(
        Long id,
        String email,
        String password,
        String name,
        ActiveStatus activeStatus,
        UserRole role,
        Timestamp createdAt,
        Timestamp updatedAt
) { }
