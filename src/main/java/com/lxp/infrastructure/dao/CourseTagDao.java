package com.lxp.infrastructure.dao;

import java.sql.SQLException;

public interface CourseTagDao {
    void save(long courseId, long tagId) throws SQLException;
}