package com.lxp.infrastructure.dao;

import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.mapper.UserMapper;
import com.lxp.infrastructure.row.UserRow;
import com.lxp.service.query.UserView;
import com.lxp.util.QueryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    public Optional<UserView> findById(Connection connection, Long id) throws SQLException {
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
                return Optional.of(UserMapper.toUserView(row));
            }
        }
        return Optional.empty();
    }


    public Optional<UserRole> findRoleById(Connection connection, Long id) throws SQLException {
        String sql = QueryType.USER_FIND_ROLE_BY_ID.getQuery();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet re = pstmt.executeQuery();

            if (re.next()) {
                UserRole role = UserRole.valueOf(re.getString("role"));
                return Optional.of(role);
            }
        }
        return Optional.empty();
    }

    public boolean existsById(Connection connection, Long id) throws SQLException {
        String sql = QueryType.USER_EXISTS_BY_ID.getQuery();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet re = pstmt.executeQuery();
            if (re.next()) {
                return re.getBoolean(1);
            }
            return false;
        }
    }
}
