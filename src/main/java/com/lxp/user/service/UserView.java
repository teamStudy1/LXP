package com.lxp.user.service;

import com.lxp.user.domain.model.enums.ActiveStatus;
import com.lxp.user.domain.model.enums.UserRole;
import java.sql.Timestamp;

public record UserView(
        Long id,
        String email,
        String password,
        String name,
        ActiveStatus activeStatus,
        UserRole role,
        String introduction,
        String resume,
        Timestamp createdAt,
        Timestamp updatedAt) {}
