package com.lxp.infrastructure.dao;

import com.lxp.config.DataSourceFactory;
import com.lxp.domain.user.enums.UserRole;
import com.lxp.infrastructure.mapper.UserMapper;
import com.lxp.infrastructure.row.UserRow;
import com.lxp.service.query.UserView;
import com.lxp.util.QueryType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
