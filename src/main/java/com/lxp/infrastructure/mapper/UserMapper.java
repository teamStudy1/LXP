package com.lxp.infrastructure.mapper;

import com.lxp.api.dto.UserResponse;
import com.lxp.infrastructure.row.UserRow;
import com.lxp.service.query.UserView;

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
}
