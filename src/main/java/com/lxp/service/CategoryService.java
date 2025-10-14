package com.lxp.service;

import com.lxp.domain.category.Category;

/*
*  Admin 관리규칙
* */
public class CategoryService {

    // DB와 통신
    private final CategoryRepository categoryRepository;
    private final AuthService authService;

    // --- 1. 새로운 카테고리 (생성)
    public Category createCategory(String name, Long parentId) {
        // -1 admin이 맞는지 검사
        authService.checkAdmin(); // Admin이 아니면 실패

        // -2 새 카테고리 만들기
        Category newCategory = new Category(name, parentId);
        Category savedCategory = categoryRepository.save(newCategory);

        //  중분류로 만든다면
        // 대분류에 하위카테고리라고 알려줘야 함
        if (parentId != null ) {
            Category parent = categoryRepository.findById(parentId).orElseThrow();
            parent.addChild((savedCategory.getParentId()));
            categoryRepository.save(parent);
        }


    }
}
