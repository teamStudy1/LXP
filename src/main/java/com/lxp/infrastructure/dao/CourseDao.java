package com.lxp.infrastructure.dao;

import com.lxp.domain.course.Course;
import com.lxp.infrastructure.row.course.CourseRow;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface CourseDao {

    CourseRow save(Course course) throws SQLException;

    Optional<CourseRow> findById(Long id) throws SQLException;

    List<CourseRow> findByTitleContaining(String keyword) throws SQLException;

    List<CourseRow> findAll() throws SQLException;

    int updateTitle(Long id, String newTitle) throws SQLException;

    default CourseRow mapRow(ResultSet rs) throws SQLException {
        return new CourseRow(rs.getLong("course_id"), rs.getString("title"), rs.getLong("instructor_id"),
                rs.getLong("category_id"), rs.getInt("total_time"), rs.getInt("total_lecture_count"),
                rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
    }
}