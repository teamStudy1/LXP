package com.lxp.service;

import com.lxp.domain.course.Course;
import com.lxp.infrastructure.dao.course.repository.JdbcCourseRepository;

public class CourseService {
    private final JdbcCourseRepository jdbcCourseRepository;

    public CourseService(
            JdbcCourseRepository jdbcCourseRepository
    ) {
        this.jdbcCourseRepository = jdbcCourseRepository;
    }

    // detail
    public Course findById(Long courseId) {
        return jdbcCourseRepository.findAggregateById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
    }







}
