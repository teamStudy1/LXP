package com.lxp.user.web.dto.response;

import com.lxp.user.domain.model.User;
import com.lxp.user.domain.model.enums.ActiveStatus;
import com.lxp.user.domain.model.enums.UserRole;
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
        Timestamp updatedAt) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getActiveStatus(),
                user.getUserRole(),
                user.getProfile().getIntroduction() == null ? "" : user.getProfile().getIntroduction(),
                user.getProfile().getResume() == null ? "" : user.getProfile().getResume(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    @Override
    public String toString() {
        return "UserResponse{"
                + "id="
                + id
                + ", email='"
                + email
                + '\''
                + ", name='"
                + name
                + '\''
                + ", activeStatus="
                + activeStatus
                + ", userRole="
                + userRole
                + ", introduction='"
                + introduction
                + '\''
                + ", resume='"
                + resume
                + '\''
                + ", createdAt="
                + createdAt
                + ", updatedAt="
                + updatedAt
                + '}';
    }
}
