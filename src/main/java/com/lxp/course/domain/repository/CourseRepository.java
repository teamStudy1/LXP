package com.lxp.course.domain.repository;

import com.lxp.course.domain.model.Course;

import java.util.Optional;

public interface CourseRepository {
    Optional<Course> findAggregateById(Long courseId);
}
