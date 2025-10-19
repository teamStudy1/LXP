package com.lxp.domain.category;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private int depth;  // 0: 최상위
    private List<Category> children;

    public Category(String name, Long parentId, int depth) {
        validateName(name);
        this.name = name;
        this.parentId = parentId;
        this.depth = parentId == null ? 0 : depth + 1;
        this.children = new ArrayList<>();
    }

    /*
    * DAO 에서 '이미 DB에 있는' 데이터를 객체로 만들 때 사용하는 생성자
    * DB에 저장된 모든 정보(ID 포함)를 받습니다
    * */

    public Category(Long id, String name, Long parentId, int depth) {
        validateName(name);
        this.id = id; // 위쪽의 기존 생성자를 먼저 호출해서 이름 ,부모 , 깊이 설정
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.children = new ArrayList<>();
    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    private void validateName(String name) {
        // isBlank()는 공백(" ")만 있는 경우도 막아줘서 더 안전합니다.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("error : 카테고리 이름은 비울 수 없습니다.");
        }
    }

    public void addChildren(Category child) {
        children.add(child);
    }

    // 자기 파일 위치 확인
    public void changeDepthAndParent(int newDepth, Long newParentId) {
        this.depth = newDepth;
        this.parentId = newParentId;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public int getDepth() { return depth; }
    public List<Category> getChildren() { return children; }

}