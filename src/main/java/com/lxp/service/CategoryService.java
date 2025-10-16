package com.lxp.service;

import com.lxp.domain.category.Category;
import com.lxp.infrastructure.dao.CategoryDao;
// 추후 추가 예정 import com.lxp.infrastructure.dao.CourseDao;

import java.util.Objects;

public class CategoryService {

    // DB와 통신
    private final CategoryDao categoryDao; // 창고관리인
    // 추후 추가 예정 ::  private final CourseDao courseDao; // 도서 목록 담당자

    public CategoryService(CategoryDao categoryDao)
                           // 추후 추가 예정 ,CourseDao courseDao
     {
        this.categoryDao = categoryDao;
       // 추후 추가 예정 :  this.courseDao = courseDao  ;
    }

    public Category createCategory(String name, Long parentId) {
        int depth = 0;
        if (parentId != null) {
            Category parent = categoryDao.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("eroor : 존재하지 않는 상위 카테고리 입니다. "));
            depth = parent.getDepth() + 1;
        }
        Category newCategory = new Category(name, parentId, depth);
        return categoryDao.save(newCategory);
    }

    public void updateCategory(Long categoryId, String newName, Long newParentId) {
        Category category = categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("error : 수정할 카테고리가 없습니다"));
        category.rename(newName);

        if (!Objects.equals(category.getParentId(), newParentId)) {
            if (Objects.equals(category.getId(), newParentId)) {
                throw new IllegalArgumentException("error : 자기 자신을 상위 카테고리로 지정할 수 없습니다");
            }

            int newDepth = 0;
            if (newParentId != null) {
                Category newParent = categoryDao.findById(newParentId)
                        .orElseThrow(() -> new IllegalArgumentException("eroor : 존재하지 않는 상위카테고리입니다 "));
                newDepth = newParent.getDepth() + 1;
            }
            category.changeDepthAndParent(newDepth, newParentId);
        }
        categoryDao.save(category);
    }

    public void deleteCategory(Long categoryId) {

        if (categoryDao.existsByParentId(categoryId)) {
            throw new IllegalArgumentException("error : 하위 카테고리가 있는 상위 카테고리는 삭제할 수 없습니다");

        }

        categoryDao.deleteById(categoryId);
       /*  추후 추가 예정
       if (courseDao.existsByCategoryId(categoryId)) {
       throw new IllegalArgumentException("error : 해당 카테고리를 사용하는 강의 있어 삭제할 수 없습니다");
       }*/

    }
}