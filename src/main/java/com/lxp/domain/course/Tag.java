package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.Objects;
//엔티티
public class Tag {
    private final Long id;
    private final String name;
    private LocalDateTime createdAt;

    public Tag(String name) {
        this.id = null;
        this.name = name;
        this.createdAt = null;
    }

    public Tag(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    // 데이터 읽고, 쓰기 위한 기본 기능들
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /**
     * '이름'이 같으면 같은 태그로 취급하기 위한 규칙입니다.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
