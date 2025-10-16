package com.lxp.domain.category;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private int depth;  // 0: 최상위

    private List<Category> children;

    // DB 조회 등을 위한 생성자
    public Category(Long id, String name, Long parentId, int depth) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.children = new ArrayList<>();
    }

    // 신규 카테고리 생성을 위한 생성자
    public Category(String name, Long parentId, int depth) {
        validateName(name); // 이름 유효성 검사
        validateParentByDepth(depth, parentId); // 이름 통일
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
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
        this.depth = newDepth;
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
    public int getDepth() { return depth; }
    public List<Category> getChildren() { return children; }
}