package com.lxp.service;

import com.lxp.domain.category.Category;
import com.lxp.infrastructure.dao.CategoryDao;
import com.lxp.service.query.CategoryView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리 관련 비즈니스 로직을 처리하는 '총 주방장' 클래스입니다.
 * '전문 요리사(CategoryDao)'에게 구체적인 요리 지시를 내리고,
 * 여러 요리(DB 작업)를 조합하여 하나의 완전한 요리(기능)를 완성합니다.
 */
public class CategoryService {

    private final CategoryDao categoryDao;

    // '총 주방장'은 '카테고리 전문 요리사(CategoryDao)'가 있어야 일할 수 있습니다.
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * 새로운 카테고리를 생성하고, 그 결과를 CategoryView로 반환합니다.
     * @return 완전히 생성된 CategoryView 객체
     */
    public CategoryView createCategory(String name, Long parentId) throws SQLException {
        int parentDepth = 0;

        // 1. Service의 역할: 필요한 '정보(컨텍스트)'를 수집합니다.
        // 여기서는 부모 카테고리의 깊이(parentDepth) 정보가 필요합니다.
        if (parentId != null) {
            CategoryView parentCategory = categoryDao.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다. ID: " + parentId));
            parentDepth = parentCategory.getDepth();
        }

        // 2. Domain 객체의 역할: 수집된 정보를 바탕으로 '비즈니스 규칙'을 실행합니다.
        // Category 생성자는 전달받은 parentDepth에 +1을 하여 자신의 깊이를 스스로 결정합니다.
        Category newCategory = new Category(name, parentId, parentDepth);

        // 3. DAO의 역할: Domain 객체의 상태를 DB에 영속화(저장)합니다.
        return categoryDao.save(newCategory);
    }

    /**
     * 전체 카테고리를 Tree 구조로 조립하여 반환합니다.
     * @return 최상위 카테고리 목록 (각 카테고리는 자식 카테고리 목록을 포함)
     */
    public List<CategoryView> getCategoryTree() throws SQLException {
        // 1. 최상위에 있는 카테고리들을 모두 가져오기
        List<CategoryView> rootCategories = categoryDao.findAllByParentId(null);

        // 2. 각각의 최상위 카테고리 아래에 있는 자식들을 찾아 붙여주기
        for (CategoryView category : rootCategories) {
            findAndSetChildren(category);
        }
        return rootCategories;
    }

    /**
     * 특정 카테고리의 자식들을 재귀적으로 찾아 설정하는 도우미 메서드입니다.
     * (재귀적: 자기 자신을 다시 호출하는 방식)
     * @param parent 부모가 될 CategoryView 객체
     */
    private void findAndSetChildren(CategoryView parent) throws SQLException {
        // 1. 현재 부모의 ID를 가지고 자식들을 모두 찾아옵니다.
        List<CategoryView> children = categoryDao.findAllByParentId(parent.getId());

        // 2. 찾아온 자식들을 현재 부모의 '자식 목록(children)'에 추가
        for (CategoryView child : children) {
            parent.addChild(child);
            // 3. 그 자식에게도 똑같은 작업을 반복시켜서, 손자, 증손자 찾아 붙이기
            findAndSetChildren(child);
        }
    }
    public void updateCategoryName(Long categoryId, String newName) throws SQLException {
        // 1. '요리사'에게 재료가 있는지 먼저 확인시킵니다. 없으면 요리 불가
        categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 카테고리를 찾을 수 없습니다. ID: " + categoryId));

        // 2. Category 도메인 객체의 생성자를 호출하여 이름 유효성 검사 규칙을 실행합니다.
        // new Category(...)를 호출하는 순간, 생성자 안에 있는 validateName()이 자동으로 실행됩니다.
        new Category(newName, null, 0); // 이름 유효성 검사만을 위한 임시 객체 생성

        // 3. 유효성 검사를 통과하면, '요리사(Dao)'에게 이름표를 바꾸라고 지시합니다.
        categoryDao.updateName(categoryId, newName);
    }

     // 카테고리를 다른 부모 아래로 이동시킵니다.
    public void moveCategory(Long categoryId, Long newParentId) throws SQLException {
        // 1. 이동시킬 재료 있는지 확인
        categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("이동할 카테고리를 찾을 수 없습니다. ID: " + categoryId));

        int newDepth;

        // 2. 새로운 부모가 누구인지에 따라 새로운 깊이를 계산
        if (newParentId == null) {
            newDepth = 0;
        } else {
            // 그 부모의 정보를 가져와서 깊이를 계산
            CategoryView newParent = categoryDao.findById(newParentId)
                    .orElseThrow(() -> new IllegalArgumentException("새로운 부모 카테고리를 찾을 수 없습니다. ID: " + newParentId));
            newDepth = newParent.getDepth() + 1;
        }

        // 3. '요리사'에게 재료의 위치와 깊이를 바꾸라고 지시
        categoryDao.updateParent(categoryId, newParentId, newDepth);
    }
    /**
     * 카테고리를 삭제합니다. 단, 자식이 없는 경우에만 삭제할 수 있습니다.
     * @param categoryId 삭제할 카테고리의 ID
     */
    public void deleteCategory(Long categoryId) throws SQLException {
        // 1. 삭제할 재료 유무 확인
        categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 카테고리를 찾을 수 없습니다. ID: " + categoryId));

        // 2. 삭제하려는 카테고리 아래에 자식 카테고리가 있는지 확인합니다.
        List<CategoryView> children = categoryDao.findAllByParentId(categoryId);
        if (!children.isEmpty()) {
            // 자식이 한 명이라도 있으면, 규칙 위반이므로 요리를 중단
            throw new IllegalStateException("하위 카테고리가 존재하므로 삭제할 수 없습니다.");
        }

        // 3. 자식이 없다는 것이 확인되면, 'DAo 요리사'에게 재료를 버리라고 지시합니다.
        categoryDao.deleteById(categoryId);
    }

    /*
    * 검색 기능
    * 이름에 특정 키워드가 포함된 카테고리 목록을 검색합니다.
    *
    * @param name 검색할 키워드
    * @return 검색 결과 CategoryView 객체들의 리스트
    * */
    public List<CategoryView> searchCategory(String name) throws SQLException {
        if (name == null || name.isEmpty()) {
            // 검색어가 비어있으면 '전문요리사' 부를 필요 없어
            // ArrayList를 보내기
            return new ArrayList<>();
        }
        return categoryDao.findByNameContaining(name);
    }
}