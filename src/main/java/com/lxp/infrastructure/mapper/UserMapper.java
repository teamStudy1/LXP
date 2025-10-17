package com.lxp.infrastructure.mapper;

import com.lxp.api.dto.UserResponse;
import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.row.user.UserProfileRow;
import com.lxp.infrastructure.row.user.UserRow;
import com.lxp.service.query.UserView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static UserView toUserView(UserRow userRow) {
        return new UserView(
                userRow.id(),
                userRow.email(),
                userRow.password(),
                userRow.name(),
                userRow.activeStatus(),
                userRow.role(),
                userRow.userProfile().introduction(),
                userRow.userProfile().resume(),
                userRow.createdAt(),
                userRow.updatedAt()
        );
    }

    public static User toDomain(UserRow userRow) {
        return new User(
                userRow.id(),
                userRow.email(),
                userRow.password(),
                userRow.name(),
                userRow.activeStatus(),
                userRow.role(),
                userRow.createdAt(),
                userRow.updatedAt()
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
                    userView.updatedAt()
            );
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
                        rs.getTimestamp("user_profile_updated_at")
                ),
                rs.getTimestamp("user_created_at"),
                rs.getTimestamp("user_updated_at")
        );
    }
}
