package com.lxp.course.service;

import com.lxp.config.TransactionManager;
import com.lxp.course.domain.model.Course;
import com.lxp.course.persistence.JdbcCourseRepository;
import com.lxp.course.web.dto.response.CourseResponse;

import java.sql.SQLException;

public class CourseService {
    private final JdbcCourseRepository jdbcCourseRepository;
    
    public CourseService(
            JdbcCourseRepository jdbcCourseRepository
    ) {
        this.jdbcCourseRepository = jdbcCourseRepository;
    }

    public CourseResponse findById(Long courseId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            Course course = jdbcCourseRepository.findAggregateById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

            TransactionManager.commit();
            return CourseResponse.from(course);
        } catch (Exception e) {
            TransactionManager.rollback();
            throw new RuntimeException("Course Detail failed", e);
        } finally {
            TransactionManager.close();
        }
    }
}
