package com.lxp.infrastructure.dao;

import javax.sql.DataSource;

public class CourseDao {
    private final DataSource dataSource;

    public CourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
