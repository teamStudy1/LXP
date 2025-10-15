package com.lxp.infrastructure.mapper;

import com.lxp.domain.user.User;
import com.lxp.infrastructure.row.UserRow;

public class UserMapper {
    public static User toUser(UserRow userRow) {
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
}
