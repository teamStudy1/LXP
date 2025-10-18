package com.lxp.service.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryView.java
 * <p>
 * 서비스 계층이 최종 결과물로 만들어내는 '완성된 요리 접시'입니다.
 * 사용자에게 보여주기 위한 순수한 데이터만을 담고 있으며,
 * 계층 구조를 표현하기 위한 자식 목록(children)을 가질 수 있습니다.
 */
public class CategoryView {
    private final Long id;
    private final String name;
    private final Long parentId;
    private final int depth;
    private final Timestamp createdAt;
    private final List<CategoryView> children = new ArrayList<>(); // 자식들을 담을 리스트

    public CategoryView(Long id, String name, Long parentId, int depth, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.createdAt = createdAt;
    }

    /**
     * Service 계층에서 자식 카테고리를 추가할 때 사용하는 메서드입니다.
     * @param child 추가할 자식 CategoryView 객체
     */
    public void addChild(CategoryView child) {
        this.children.add(child);
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public int getDepth() { return depth; }
    public Timestamp getCreatedAt() { return createdAt; }
    public List<CategoryView> getChildren() { return children; }

    /**
     * 객체의 상태를 보기 쉽게 출력하기 위한 toString 메서드입니다.
     * 재귀적으로 모든 자식을 출력하면 너무 길어지므로, 자식의 개수만 표시합니다.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 깊이에 따라 들여쓰기를 추가하여 계층 구조를 시각적으로 표현합니다.
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        sb.append("L ")
                .append(name)
                .append(" (ID: ").append(id)
                .append(", 자식 수: ").append(children.size())
                .append(")");

        // 자식들 재귀적 출력
        for (CategoryView child : children) {
            sb.append("\n").append(child.toString());
        }

        return sb.toString();
    }
}

