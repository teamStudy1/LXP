package com.lxp.infrastructure.mapper;

import com.lxp.domain.user.User;
import com.lxp.infrastructure.row.UserRow;

public class UserMapper {
    public static User toUser(UserRow userRow) {
        return new User(
                userRow.id(),
                userRow.name(),
                userRow.password(),
                userRow.nickname(),
                userRow.activeStatus(),
                userRow.role(),
                userRow.createdAt(),
                userRow.updatedAt()

        );
    }
}
