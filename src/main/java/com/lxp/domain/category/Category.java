package com.lxp.domain.category;

import java.util.Objects;

public class Category {

    public enum CategoryLevel {
        LARGE,  // 대분류
        MEDIUM, // 중분류
        SMALL   // 소분류
    }

    private Long id;
    private String name;
    private Long parentId;
    private CategoryLevel level;

    // DB 조회 등을 위한 생성자
    public Category(Long id, String name, Long parentId, CategoryLevel level) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
    }

    // 신규 카테고리 생성을 위한 생성자
    public Category(String name, Long parentId, CategoryLevel level) {
        validateName(name); // 이름 유효성 검사
        validateParentByLevel(level, parentId); // 레벨 규칙 검사
        this.name = name;
        this.parentId = parentId;
        this.level = level;
    }

    /**
     * 카테고리 이름을 변경합니다.
     */
    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    /**
     * [중요] CategoryService에 의해서만 호출되어야 하는 내부 상태 변경 메서드.
     * Service가 외부 규칙을 모두 검증했다고 가정하고 내부 상태만 변경한다.
     */
    void changeLevelAndParent(CategoryLevel newLevel, Long newParentId) {
        // 객체 스스로의 최소한의 규칙(내부 일관성)만 검사
        validateParentByLevel(newLevel, newParentId);
        this.level = newLevel;
        this.parentId = newParentId;
    }

    // --- Private 유효성 검사 메서드 ---

    private void validateName(String name) {
        // isBlank()는 공백(" ")만 있는 경우도 막아줘서 더 안전합니다.
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("error : 카테고리 이름은 비울 수 없습니다.");
        }
    }

    private void validateParentByLevel(CategoryLevel currentLevel, Long parentId) {
        if (currentLevel == CategoryLevel.LARGE && parentId != null) {
            throw new IllegalArgumentException("error : 대분류는 상위 카테고리를 가질 수 없습니다.");
        }
        if ((currentLevel == CategoryLevel.MEDIUM || currentLevel == CategoryLevel.SMALL) && parentId == null) {
            throw new IllegalArgumentException("error : 상위 카테고리가 필요합니다.");
        }
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public CategoryLevel getLevel() { return level; }
}