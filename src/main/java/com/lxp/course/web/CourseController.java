package com.lxp.course.web;

import com.lxp.course.domain.model.Course;
import com.lxp.course.service.CourseService;
import com.lxp.course.web.dto.response.CourseResponse;

import java.sql.SQLException;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseResponse findById(Long courseId) throws SQLException {
        return courseService.findById(courseId);
    }
}
