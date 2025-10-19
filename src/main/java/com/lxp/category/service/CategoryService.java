package com.lxp.category.service;

import com.lxp.category.domain.Category;
import com.lxp.category.persistance.JdbcCategoryRepository;
import com.lxp.category.web.dto.response.CategoryResponse;
import com.lxp.config.TransactionManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final JdbcCategoryRepository jdbcCategoryRepository;

    public CategoryService(JdbcCategoryRepository jdbcCategoryRepository) {
        this.jdbcCategoryRepository = jdbcCategoryRepository;
    }

    // TODO (반환값 boolean)
    public void createCategory(String name, Long parentId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            Category parentCategory = null;
            if (parentId != null) {
                parentCategory =
                        jdbcCategoryRepository
                                .findById(parentId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다. ID: " + parentId));
            }

            Category newCategory = Category.createNew(name, parentCategory);

            jdbcCategoryRepository.save(newCategory);
            TransactionManager.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            TransactionManager.rollback();
            throw new RuntimeException("Course save failed", e);
        } finally {
            TransactionManager.close();
        }
    }

    public CategoryResponse findById(Long categoryId) throws SQLException {
        Category category = jdbcCategoryRepository.findAllByParents(categoryId);
        return CategoryResponse.from(category);
    }

    public List<CategoryResponse> findAll() throws SQLException {
        List<Category> categories = jdbcCategoryRepository.findAll();
        return categories.stream().map(CategoryResponse::from).toList();
    }

    public void updateCategoryName(Long categoryId, String newName) throws SQLException {
        Category category =
                jdbcCategoryRepository
                        .findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + categoryId));

        category.rename(newName);
        jdbcCategoryRepository.update(category);
    }

    public void moveCategory(Long categoryId, Long newParentId) throws SQLException {

        Category category =
                jdbcCategoryRepository
                        .findById(categoryId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("이동할 카테고리를 찾을 수 없습니다. ID: " + categoryId));

        Category parentCategory =
                jdbcCategoryRepository
                        .findById(newParentId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("이동할 카테고리를 찾을 수 없습니다. ID: " + categoryId));

        category.setParent(parentCategory);

        jdbcCategoryRepository.update(category);
    }

    public List<CategoryResponse> searchCategory(String name) throws SQLException {
        if (name == null || name.isEmpty()) {
            return new ArrayList<>();
        }
        return jdbcCategoryRepository.searchName(name).stream().map(CategoryResponse::from).toList();
    }
}
