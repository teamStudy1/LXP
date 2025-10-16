package com.lxp.infrastructure.mapper;

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
}
