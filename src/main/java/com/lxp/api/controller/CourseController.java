package com.lxp.api.controller;

import com.lxp.service.CourseService;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public void findById(Long courseId){
        courseService.findById(courseId);
    }
}
