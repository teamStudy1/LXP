package com.lxp.course.domain.repository;

import com.lxp.course.domain.model.Course;
import com.lxp.course.domain.model.Tag;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository {
    Optional<Course> findAggregateById(Long courseId);

    Set<Tag> findOrCreateByNames(List<String> tagNames) throws SQLException;

    void save(Course course);

    List<Course> findAll();

    List<Course> findByTitleContaining(String keyword) throws SQLException;

    Optional<Course> findById(Long courseId);

    Course update(Course course) throws SQLException;
}
