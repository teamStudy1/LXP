package com.lxp.category.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private Category parent;
    private int depth; // 0: 최상위
    private List<Category> children;

    public Category(Long id, String name, int depth) {
        this.id = id;
        this.name = name;
        this.depth = depth;
        this.children = new ArrayList<>();
    }

    public static Category createNew(String name, Category parent) {
        validateName(name);
        int depth = (parent == null) ? 0 : parent.getDepth() + 1;
        Category newCategory = new Category(null, name, depth); // 새 카테고리는 id가 없음
        if (parent != null) {
            newCategory.setParent(parent);
            parent.addChildren(newCategory);
        }
        return newCategory;
    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("카테고리 이름은 비울 수 없습니다.");
        }
    }

    public void addChildren(Category child) {
        this.children.add(child);
        child.setParent(this); // 양방향 연관관계 설정
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public List<Category> getChildren() {
        return List.copyOf(children);
    } // 불변 리스트 반환
}
