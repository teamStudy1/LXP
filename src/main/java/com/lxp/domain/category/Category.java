package com.lxp.domain.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * 상위 카테고리를 변경
     * @param newParentId 새로운 상위 카테고리 ID
     * */

    public void changeParentId(Long newParentId) {
        // 자기 자신을 상위 카테고리로 지정할 수 없음
        if (Objects.equals(newParentId, this.parentId)) {
            throw new IllegalArgumentException("자기 자신을 상위 카테고리로 지정할 수 없습니다");
        }
        // 하위 카테고리를 상위 카테고리로 지정할 수 없습니다
        if (this.childrenIds.contains(newParentId)) {
            throw new IllegalArgumentException("하위 카테고리를 상위 카테고리로 지정할 수 없습니다");
        }
        this.parentId = newParentId;
    }

    /*
     * @Param 하위 카테고리 추가/제거
     * */

    public void addChild(Long childID) {
        this.childrenIds.add(childID);

    }

    public void removeChild(Long childId) {
        this.childrenIds.remove(childId);
    }

    /* Getters*/
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public List<Long> getChildrenIds() {
        return childrenIds;
    }

    /* setter (유효성 검사)
     * @param name 새로운 카테고리 이름
     * */

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("카테고리 이름은 비워둘 수 없습니다. ");
        }
        this.name = name;
    }
}