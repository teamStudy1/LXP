package com.lxp.api.dto;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;

import java.sql.Timestamp;

public record UserResponse(
        Long id,
        String email,
        String name,
        ActiveStatus activeStatus,
        UserRole userRole,
        String introduction,
        String resume,
        Timestamp createdAt,
        Timestamp updatedAt
) {
    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", activeStatus=" + activeStatus +
                ", userRole=" + userRole +
                ", introduction='" + introduction + '\'' +
                ", resume='" + resume + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
