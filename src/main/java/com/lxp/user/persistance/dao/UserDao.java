package com.lxp.user.persistance.dao;

import com.lxp.user.domain.model.enums.UserRole;
import com.lxp.user.persistance.mapper.UserMapper;
import com.lxp.user.persistance.row.UserRow;
import com.lxp.util.QueryType;
import java.sql.*;
import java.util.Optional;
import javax.sql.DataSource;

public class UserDao {
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<UserRow> findUserById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_BY_ID.getQuery();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserRow row = UserMapper.fromResultSet(rs);
                return Optional.of(row);
            }
        }
        return Optional.empty();
    }

    public Optional<UserRole> findRoleById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_ROLE_BY_ID.getQuery();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserRole role = UserRole.valueOf(rs.getString("role"));
                return Optional.of(role);
            }
        }
        return Optional.empty();
    }

    public boolean existsById(Long id) throws SQLException {
        String sql = QueryType.USER_EXISTS_BY_ID.getQuery();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        }
    }

    public boolean existsByEmail(String email) throws SQLException {
        String sql = QueryType.USER_EXISTS_BY_EMAIL.getQuery();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        }
    }

    public Long saveUser(UserRow row) throws SQLException {
        String sql = QueryType.USER_SAVE.getQuery();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt =
                connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, row.email());
            pstmt.setString(2, row.password());
            pstmt.setString(3, row.name());
            int result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        return null;
    }

    public int update(UserRow row) throws SQLException {
        String sql = QueryType.USER_UPDATE.getQuery();
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, row.role().name());
            pstmt.setString(2, row.activeStatus().name());
            pstmt.setLong(3, row.id());

            return pstmt.executeUpdate();
        }
    }
}
