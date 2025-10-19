package com.lxp.user.persistance.mapper;

import com.lxp.user.domain.model.UserProfile;
import com.lxp.user.web.dto.response.UserResponse;
import com.lxp.user.domain.model.User;
import com.lxp.user.domain.model.enums.ActiveStatus;
import com.lxp.user.domain.model.enums.UserRole;
import com.lxp.user.persistance.row.UserProfileRow;
import com.lxp.user.persistance.row.UserRow;
import com.lxp.user.service.UserView;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User toDomain(UserRow userRow) {
        return new User(
                userRow.id(),
                userRow.email(),
                userRow.password(),
                userRow.name(),
                userRow.activeStatus(),
                userRow.role(),
                new UserProfile(
                    userRow.userProfile().introduction(),
                    userRow.userProfile().resume()
                ),
                userRow.createdAt(),
                userRow.updatedAt());
    }

    public static UserRow toRow(User user) {
        return new UserRow(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.getActiveStatus(),
            user.getRole(),
            new UserProfileRow(
                user.getProfile().getId(),
                user.getProfile().getIntroduction(),
                user.getProfile().getResume(),
                user.getProfile().getCreatedAt(),
                user.getProfile().getUpdatedAt()
            ),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public static UserResponse toUserResponse(UserView userView) {
        return new UserResponse(
                userView.id(),
                userView.email(),
                userView.name(),
                userView.activeStatus(),
                userView.role(),
                userView.introduction(),
                userView.resume(),
                userView.createdAt(),
                userView.updatedAt());
    }

    public static UserRow fromResultSet(ResultSet rs) throws SQLException {
        return new UserRow(
                rs.getLong("user_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("name"),
                ActiveStatus.valueOf(rs.getString("active_status")),
                UserRole.valueOf(rs.getString("role")),
                new UserProfileRow(
                        rs.getLong("user_profile_id"),
                        rs.getString("introduction"),
                        rs.getString("resume"),
                        rs.getTimestamp("user_profile_created_at"),
                        rs.getTimestamp("user_profile_updated_at")),
                rs.getTimestamp("user_created_at"),
                rs.getTimestamp("user_updated_at"));
    }
}
