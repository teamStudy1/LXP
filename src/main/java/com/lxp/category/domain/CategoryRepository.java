package com.lxp.category.domain;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category findAllByParents(Long categoryId) throws SQLException;
    ;

    Category findAllByDescendants(Long categoryId) throws SQLException;
    ;

    Optional<Category> findById(Long categoryId) throws SQLException;
    ;

    void save(Category newCategory) throws SQLException;
    ;

    List<Category> findAll() throws SQLException;
    ;

    List<Category> searchName(String name) throws SQLException;
    ;
}
