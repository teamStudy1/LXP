package com.lxp.infrastructure.dao;

import com.lxp.domain.course.Tag;
import java.sql.SQLException;
import java.util.Optional;

public interface TagDao {
    Optional<Tag> findByName(String name) throws SQLException;
    Tag save(String name) throws SQLException;
}