package com.lxp.domain.course;

import java.util.Optional;

public interface CourseRepository {
    Optional<Course> findAggregateById(Long courseId);
}
