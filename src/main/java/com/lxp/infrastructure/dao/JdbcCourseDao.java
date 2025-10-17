package com.lxp.infrastructure.dao;

import com.lxp.domain.course.Course;
import com.lxp.infrastructure.row.course.CourseRow;
import com.lxp.util.QueryType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCourseDao implements CourseDao{
    private final DataSource dataSource;

    public JdbcCourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CourseRow save(Course course) throws SQLException {
        String sql = QueryType.COURSE_SAVE.getQuery();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setLong(2, course.getInstructorId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long newId = generatedKeys.getLong(1);
                return findById(newId).orElseThrow(() -> new SQLException("Save failed, could not retrieve the saved course."));
            } else {
                throw new SQLException("Creating course failed, no ID obtained.");
            }
        }
    }

    @Override
    public Optional<CourseRow> findById(Long id) throws SQLException {
        String sql = QueryType.COURSE_FIND_BY_ID.getQuery();
        try (Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CourseRow> findByTitleContaining(String keyword) throws SQLException {
        List<CourseRow> rows = new ArrayList<>();
        String sql = QueryType.COURSE_FIND_BY_TITLE.getQuery();
        try (Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rows.add(mapRow(rs));
            }
        }
        return rows;
    }

    @Override
    public List<CourseRow> findAll() throws SQLException {
        List<CourseRow> rows = new ArrayList<>();
        String sql = QueryType.COURSE_FIND_ALL.getQuery();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                rows.add(mapRow(rs));
            }
        }
        return rows;
    }

    @Override
    public int updateTitle(Long id, String newTitle) throws SQLException {
        String sql = QueryType.COURSE_UPDATE_TITLE.getQuery();
        try (Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate();
        }
    }

    public CourseRow mapRow(ResultSet rs) throws SQLException {
        return new CourseRow(rs.getLong("course_id"), rs.getString("title"), rs.getLong("instructor_id"),
                rs.getLong("category_id"), rs.getInt("total_time"), rs.getInt("total_lecture_count"),
                rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
    }
}
