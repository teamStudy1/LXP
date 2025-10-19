package com.lxp.course.web;

import com.lxp.course.service.CourseService;
import com.lxp.course.web.dto.request.CourseRequest;
import com.lxp.course.web.dto.request.CourseUpdateRequest;
import com.lxp.course.web.dto.response.CourseAllResponse;
import com.lxp.course.web.dto.response.CourseResponse;
import java.sql.SQLException;
import java.util.List;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseResponse findById(Long courseId) throws SQLException {
        return courseService.findById(courseId);
    }

    public void save(CourseRequest courseRequest) throws SQLException {
        courseService.save(courseRequest);
    }

    public List<CourseAllResponse> findAll() throws SQLException {
        return courseService.findAll();
    }

    public List<CourseAllResponse> findAllByKeyword(String keyword) throws SQLException {
        return courseService.searchCoursesByKeyword(keyword);
    }

    public void updateCourseInfo(CourseUpdateRequest request) throws SQLException {
        courseService.updateCourse(request);
    }
}
