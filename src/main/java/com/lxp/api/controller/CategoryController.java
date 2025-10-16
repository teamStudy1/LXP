package com.lxp.api.controller;

import com.lxp.domain.category.Category;
import com.lxp.service.CategoryService;

public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*
    * @param name 생성할 카테고리의 이름
    * @param parentId 상위 카테고리의 ID (없으면 null)
    * @return 생성된 Catetory 객체
    * */

    public Category createCategory(String name, Long parentId) {
        return categoryService.createCategory(name, parentId);
    }

    public void updateCategory(Long categoryId, String newName, Long newParentId) {
        categoryService.updateCategory(categoryId, newName, newParentId);
    }

    /*
    * 카테고리 삭제하는 기능 호출
    * @param categoryId 삭제할 카테고리 ID
    * */
    public void deleteCategory(Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}

