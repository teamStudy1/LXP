package com.lxp.infrastructure.mapper;

import com.lxp.api.dto.UserResponse;
import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.row.UserRow;
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
                userRow.createdAt(),
                userRow.updatedAt()
        );
    }

    public static UserResponse toUserResponse(UserView userView) {
            return new UserResponse(
                    userView.email(),
                    userView.name(),
                    userView.activeStatus(),
                    userView.role(),
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
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
}
