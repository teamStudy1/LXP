package com.lxp.domain.category;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private Integer parentsdepth;  // 0: 최상위
    private List<Category> children;

    // DB 조회 등을 위한 생성자
    public Category(Long id, String name, Long parentId, int depth) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentsdepth = depth;
        this.children = new ArrayList<>();
    }

    // --- 변경점 1: 신규 생성을 위한 생성자를 private으로 변경 ---
    // 외부에서 new Category(...) 로 직접 생성하는 것을 막고, 정적 팩토리 메서드를 사용하도록 강제합니다.
    private Category(String name, Long parentId, Integer parentdepth) {
        validateName(name);
        validateParentByDepth(parentdepth, parentId);
        this.name = name;
        this.parentId = parentId;
        this.parentsdepth = parentdepth;
        this.children = new ArrayList<>();
    }
    public static Category createSubCategory(String name, Category parent) {
        if (parent == null) {
            throw new IllegalArgumentException("error : 하위 카테고리 생성 시 부모 카테고리는 필수입니다.");
        }
        // ✨ 핵심 로직: 서비스의 depth 계산 로직을 객체가 스스로 처리합니다.
        int newDepth = parent.getDepth() + 1;

        return new Category(name, parent.getId(), newDepth);
    }

    /**
     * 카테고리 이름을 변경.
     */
    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    /*
     * [추가된 메서드]
     * CategoryService에 의해서만 호출되어야 하는 내부 상태 변경 메서드.
     * Service가 외부 규칙(예: 새 부모의 depth가 올바른지)을 모두 검증했다고 가정
     */
    public void changeDepthAndParent(int newDepth, Long newParentId) {
        // 객체 스스로의 최소한의 규칙(내부 일관성)만 검사
        validateParentByDepth(newDepth, newParentId);
        this.parentsdepth = newDepth;
        this.parentId = newParentId;
    }

    // --- Private 유효성 검사 메서드 ---

    private void validateName(String name) {
        // isBlank()는 공백(" ")만 있는 경우도 막아줘서 더 안전합니다.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("error : 카테고리 이름은 비울 수 없습니다.");
        }
    }

    private void validateParentByDepth(int depth, Long parentId) {

        // 최상위인데 부모를 생성하려고 시도 하는 경우 -> 에러
        if (depth == 0 && parentId != null) {
            throw new IllegalArgumentException("error : 대분류에서 대분류를 만들 수 없습니다.");
        }

        // depth 가 1이상인데 부모가 없을경우 -> 에러
        if (depth > 0 && parentId == null)  {
            throw new IllegalArgumentException("error : 상위 카테고리가 필요합니다.");
        }
    }

    public void addChidren(Category child) {
        children.add(child);
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public int getDepth() { return parentsdepth; }
    public List<Category> getChildren() { return children; }
}