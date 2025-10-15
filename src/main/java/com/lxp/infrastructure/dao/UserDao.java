package com.lxp.infrastructure.dao;

import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.mapper.UserMapper;
import com.lxp.infrastructure.row.UserRow;
import com.lxp.util.QueryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    public Optional<User> findById(Connection connection, Long id) throws SQLException {
        String sql = QueryType.USER_FIND_BY_ID.getQuery();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet re = pstmt.executeQuery();

            if (re.next()) {
                UserRow row =
                        new UserRow(
                                re.getLong("user_id"),
                                re.getString("email"),
                                re.getString("password"),
                                re.getString("name"),
                                ActiveStatus.valueOf(re.getString("active_status")),
                                UserRole.valueOf(re.getString("role")),
                                re.getTimestamp("created_at"),
                                re.getTimestamp("updated_at"));
                return Optional.of(UserMapper.toUser(row));
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByName(Connection connection, String name) throws SQLException {
        String sql = QueryType.USER_FIND_BY_NAME.getQuery();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet re = pstmt.executeQuery();

            if (re.next()) {
                UserRow row =
                        new UserRow(
                                re.getLong("user_id"),
                                re.getString("email"),
                                re.getString("password"),
                                re.getString("name"),
                                ActiveStatus.valueOf(re.getString("active_status")),
                                UserRole.valueOf(re.getString("role")),
                                re.getTimestamp("created_at"),
                                re.getTimestamp("updated_at"));
                return Optional.of(UserMapper.toUser(row));
            }
        }
        return Optional.empty();
    }
}
