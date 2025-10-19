package com.lxp.infrastructure.dao;

import com.lxp.config.JDBCConnection;
import com.lxp.config.TransactionManager;
import com.lxp.domain.course.Tag;
import com.lxp.util.QueryType;

import java.sql.*;
import java.util.Optional;

public class JdbcTagDao implements TagDao {

    @Override
    public Optional<Tag> findByName(String name) throws SQLException {
        String sql = QueryType.TAG_FIND_BY_NAME.getQuery();
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Tag(rs.getLong("tag_id"), rs.getString("name"), rs.getTimestamp("created_at").toLocalDateTime()));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Tag save(String name) throws SQLException {
        String sql = QueryType.TAG_SAVE.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long newId = rs.getLong(1);
                    return new Tag(newId, name, null);
                } else {
                    throw new SQLException("Creating tag failed, no ID obtained.");
                }
            }
        }
    }
}