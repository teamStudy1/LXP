package com.lxp.infrastructure.dao;

import com.lxp.config.TransactionManager;
import com.lxp.util.QueryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcCourseDetailDao implements CourseDetailDao {

    @Override
    public void save(Long courseId, String content, String contentDetail) throws SQLException {
        String sql = QueryType.COURSE_DETAIL_SAVE.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, courseId);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, contentDetail);
            preparedStatement.executeUpdate();
        }
    }
}