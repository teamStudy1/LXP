package com.lxp.api.dto;

import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;

import java.sql.Timestamp;

public record UserResponse(
        String name,
        String nickname,
        ActiveStatus activeStatus,
        UserRole userRole,
        Timestamp createdAt,
        Timestamp updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getName(),
                user.getNickname(),
                user.getActiveStatus(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", activeStatus=" + activeStatus +
                ", userRole=" + userRole +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "} \n";
    }
}
