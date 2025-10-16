package com.lxp.service;

import com.lxp.infrastructure.dao.CourseDao;

public class CourseService {
    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
}
