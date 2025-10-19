package com.lxp.infrastructure.dao;

import com.lxp.config.TransactionManager;
import com.lxp.util.QueryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcCourseTagDao implements CourseTagDao {
    @Override
    public void save(long courseId, long tagId) throws SQLException {
        String sql = QueryType.COURSE_TAG_SAVE.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            ps.setLong(2, tagId);
            ps.executeUpdate();
        }
    }
}