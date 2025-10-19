package com.lxp.infrastructure.dao;

import java.sql.SQLException;

public interface CourseDetailDao {
    void save(Long courseId, String content, String contentDetail) throws SQLException;
}
