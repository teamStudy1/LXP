package com.lxp.infrastructure.dao;

import com.lxp.config.TransactionManager;
import com.lxp.domain.course.Course;
import com.lxp.infrastructure.row.course.CourseRow;
import com.lxp.util.QueryType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCourseDao implements CourseDao {
    private final DataSource dataSource;

    public JdbcCourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CourseRow save(Course course) throws SQLException {
        String sql = QueryType.COURSE_SAVE.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setLong(2, course.getInstructorId());
            preparedStatement.setDouble(3, course.getTotalTime());
            preparedStatement.setInt(4, course.getTotalLectureCount());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long newId = generatedKeys.getLong(1);
                return new CourseRow(newId, course.getTitle(), course.getInstructorId(), null,
                        course.getContent(), course.getContentDetail(), null,
                        course.getTotalTime(), course.getTotalLectureCount(),
                        new Timestamp(System.currentTimeMillis()),
                        new Timestamp(System.currentTimeMillis()));
            } else {
                throw new SQLException("Creating course failed, no ID obtained.");
            }
        }
    }

    @Override
    public int updateTitle(Long id, String newTitle) throws SQLException {
        String sql = QueryType.COURSE_UPDATE_TITLE.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate();
        }
    }


    private CourseRow mapRow(ResultSet rs) throws SQLException {
        return new CourseRow(rs.getLong("course_id"), rs.getString("title"), rs.getLong("instructor_id"),
                rs.getString("instructor_name"), rs.getString("content"), rs.getString("content_detail"),
                rs.getLong("category_id"), rs.getDouble("total_time"), rs.getInt("total_lecture_count"),
                rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
    }

    @Override
    public Optional<CourseRow> findById(Long id) throws SQLException {
        String sql = QueryType.COURSE_FIND_BY_ID.getQuery();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CourseRow> findByTitleContaining(String keyword) throws SQLException {
        List<CourseRow> rows = new ArrayList<>();
        String sql = QueryType.COURSE_FIND_BY_TITLE.getQuery();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    rows.add(mapRow(rs));
                }
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
}