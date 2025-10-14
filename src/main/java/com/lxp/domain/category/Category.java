package com.lxp.domain.category;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private Long id; // 카테고리 고유ID
    private String name; // 카테고리 이름
    private Long parentId; // 내가 속한 상위 카테고리 번호
    private List<Long> childrenIds; // 내가속한 하위 카테고리 번호

    public Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
        this.childrenIds = new ArrayList<>();
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("카테고리 이름은 비워둘 수 없습니다.");
        }
        this.name = newName;

    }
    /*
     * Public : 새로운 하위 카테고리를 추가
     * @Param childId 새로 생긴 하위 카테고리 ID
     * */

    public void addChild(Long childID) {
        this.childrenIds.add(childID);
    }

    /*
     * 하위 카테고리를 목록에서 제거
     * */

    public void removeChild(Long childId) {
        this.childrenIds.remove(childId);
    }
    public Long getId() { return id; }
    public String getName() {return name; }
    public Long getParentId() {return parentId; }
    public List<Long> getChildrenIds() { return childrenIds; }
    }
