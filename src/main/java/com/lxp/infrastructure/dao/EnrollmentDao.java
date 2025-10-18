package com.lxp.infrastructure.dao;

import com.lxp.config.TransactionManager;
import com.lxp.util.QueryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnrollmentDao {

    public void save(Long userId, Long courseId) throws SQLException {
        String sql = QueryType.ENROLLMENT_SAVE.getQuery();
        try (Connection conn = TransactionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, courseId);
            preparedStatement.executeUpdate();
        }
    }

    public int delete(Long userId, Long courseId) throws SQLException {
        String sql = QueryType.ENROLLMENT_DELETE.getQuery();
        try (Connection conn = TransactionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, courseId);
            return preparedStatement.executeUpdate();
        }
    }
}