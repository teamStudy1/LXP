package com.lxp.api.controller;

import com.lxp.api.dto.CourseView;
import com.lxp.domain.course.Course;
import com.lxp.service.CourseService;
import java.sql.SQLException;
import java.util.List;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public List<CourseView> getAllCourses() throws SQLException {
        return courseService.getAllCourses();
    }

    public List<CourseView> searchCoursesByTitle(String keyword) throws SQLException {
        return courseService.searchCoursesByTitle(keyword);
    }

    public CourseView getCourseById(Long id) throws SQLException {
        return courseService.getCourseById(id);
    }

    public Course createCourse(String title, Long instructorId) throws SQLException {
        return courseService.createCourse(title, instructorId);
    }

    public boolean updateCourseTitle(Long id, String newTitle) throws SQLException {
        return courseService.updateCourseTitle(id, newTitle);
    }
}