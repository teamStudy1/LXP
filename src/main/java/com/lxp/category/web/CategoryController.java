package com.lxp.category.web;

import com.lxp.category.service.CategoryService;
import com.lxp.category.web.dto.response.CategoryResponse;
import java.sql.SQLException;
import java.util.List;

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void createCategory(String name, Long parentId) throws SQLException {
        categoryService.createCategory(name, parentId);
    }

    public List<CategoryResponse> getCategoryTree() throws SQLException {
        return categoryService.findAll();
    }

    public void updateCategoryName(Long categoryId, String newName) throws SQLException {
        categoryService.updateCategoryName(categoryId, newName);
    }

    public void moveCategory(Long categoryId, Long newParentId) throws SQLException {
        categoryService.moveCategory(categoryId, newParentId);
    }

    public List<CategoryResponse> searchCategoryByName(String name) throws SQLException {
        return categoryService.searchCategory(name);
    }
}
