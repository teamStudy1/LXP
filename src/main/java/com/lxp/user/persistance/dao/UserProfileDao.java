package com.lxp.user.persistance.dao;

import com.lxp.user.persistance.row.UserProfileRow;
import com.lxp.util.QueryType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class UserProfileDao {
    private DataSource dataSource;

    public UserProfileDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveUserProfile(Long userId, UserProfileRow row) throws SQLException {
        String sql = QueryType.USER_PROFILE.getQuery();
        try (PreparedStatement pstmt =
                dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, row.introduction());
            pstmt.setString(3, row.resume());
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
}
