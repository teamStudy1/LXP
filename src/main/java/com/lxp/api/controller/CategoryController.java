package com.lxp.api.controller;

import com.lxp.service.query.CategoryView;
import com.lxp.service.CategoryService;

import java.sql.SQLException;
import java.util.List;


/*
* '홀 매니저'의 클래스
* 서버의 요청을 Service에게 전달 하는 중간다리 역할
* */
public class CategoryController {

    private final CategoryService categoryService;

    // '홀 매니저'는 'Service'가 누군인지 알아야 함
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // '카테고리 만들기' 요청을 그대로 전달
    public CategoryView createCategory(String name, Long parentId) throws SQLException, ClassNotFoundException {
        return categoryService.createCategory(name, parentId);
    }

    // ' 카테고리 트리 보여줘' 요청을 그대로 전달 합니다.
    public List<CategoryView> getCategoryTree() throws SQLException {
        return categoryService.getCategoryTree();
    }

    // ' 카테고리 이름 바꿔줘' 요청을 그대로 전달합니다
    public void updateCategoryName(Long categoryId, String newName) throws SQLException {
        categoryService.updateCategoryName(categoryId, newName);
    }

    // ' 카테고리 옮겨줘' 요청을 그대로 전달합니다.
    public void moveCategory(Long categoryId, Long newParentId) throws SQLException {
        categoryService.moveCategory(categoryId, newParentId);
    }

    // ' 카테고리 삭제' 요청을 그대로 전달
    public void deleteCategory(Long categoryId) throws SQLException {
        categoryService.deleteCategory(categoryId);
    }


    // '이름으로 카테고리 검색' 요청을 그대로 전달
    public List<CategoryView> searchCategoryByName(String name) throws SQLException {
        return categoryService.searchCategory(name);
    }
}
