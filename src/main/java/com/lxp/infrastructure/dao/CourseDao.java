package com.lxp.infrastructure.dao;

import com.lxp.domain.course.Course;
import com.lxp.infrastructure.row.course.CourseRow;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseDao {

    CourseRow save(Course course) throws SQLException;

    Optional<CourseRow> findById(Long id) throws SQLException;

    List<CourseRow> findByTitleContaining(String keyword) throws SQLException;

    List<CourseRow> findAll() throws SQLException;

    int updateTitle(Long id, String newTitle) throws SQLException;
}