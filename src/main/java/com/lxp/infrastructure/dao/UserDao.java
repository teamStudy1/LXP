package com.lxp.infrastructure.dao;

import com.lxp.domain.user.User;
import com.lxp.domain.user.enums.ActiveStatus;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.mapper.UserMapper;
import com.lxp.infrastructure.row.user.UserRow;
import com.lxp.service.query.UserView;
import com.lxp.util.QueryType;
import java.sql.*;
import java.util.Optional;
import javax.sql.DataSource;

public class UserDao {
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<UserView> findUserViewById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_BY_ID.getQuery();
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserRow row = UserMapper.fromResultSet(rs);
                return Optional.of(UserMapper.toUserView(row));
            }
        }
        return Optional.empty();
    }

    public Optional<UserRole> findRoleById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_ROLE_BY_ID.getQuery();
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        }
    }

    public Long saveUser(String email, String password, String name) throws SQLException {
        String sql = QueryType.USER_SAVE.getQuery();
        try (PreparedStatement pstmt =
                dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public Long saveUserProfile(Long userId, String introduction, String resume) throws SQLException {
        String sql = QueryType.USER_PROFILE.getQuery();
        try (PreparedStatement pstmt =
                dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, introduction);
            pstmt.setString(3, resume);
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

    public Optional<User> findUserById(Long id) throws SQLException {
        String sql = QueryType.USER_FIND_BY_ID.getQuery();
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserRow row = UserMapper.fromResultSet(rs);
                return Optional.of(UserMapper.toDomain(row));
            }
        }
        return Optional.empty();
    }

    public int updateUserRole(Long userId, UserRole userRole) throws SQLException {
        String sql = QueryType.USER_UPDATE_ROLE.getQuery();
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, userRole.name());
            pstmt.setLong(2, userId);

            return pstmt.executeUpdate();
        }
    }

    public int updateUserActiveStatus(Long userId, ActiveStatus activeStatus) throws SQLException {
        String sql = QueryType.USER_UPDATE_ACTIVE_STATUS.getQuery();
        try (PreparedStatement pstmt = dataSource.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, activeStatus.name());
            pstmt.setLong(2, userId);

            return pstmt.executeUpdate();
        }
    }
}
