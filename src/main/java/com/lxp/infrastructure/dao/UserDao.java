package com.lxp.infrastructure.dao;

import com.lxp.config.DataSourceFactory;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.mapper.UserMapper;
import com.lxp.infrastructure.row.UserRow;
import com.lxp.service.query.UserView;
import com.lxp.util.QueryType;

import java.sql.*;
import java.util.Optional;

public class UserDao {

    public Optional<UserView> findById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_BY_ID.getQuery();
        try (PreparedStatement pstmt = DataSourceFactory.getDataSource().getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserRow row =
                        UserMapper.fromResultSet(rs);
                return Optional.of(UserMapper.toUserView(row));
            }
        }
        return Optional.empty();
    }


    public Optional<UserRole> findRoleById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_ROLE_BY_ID.getQuery();
        try (PreparedStatement pstmt = DataSourceFactory.getDataSource().getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = DataSourceFactory.getDataSource().getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = DataSourceFactory.getDataSource().getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        }
    }

    public Long saveUser(Connection connection, String email, String password, String name) throws SQLException {
        String sql = QueryType.USER_SAVE.getQuery();
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
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

    public Long saveUserProfile(Connection connection, Long userId, String introduction, String resume) throws SQLException {
        String sql = QueryType.USER_PROFILE.getQuery();
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, introduction);
            pstmt.setString(3, resume);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                try(ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        return null;
    }
}
