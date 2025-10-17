package com.lxp.course.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

// 엔티티
public class Tag {
    private final Long id; // Long 타입이라 아직 DB에 저장 안된 상태(null) 표현 가능
    private final String name;
    private final LocalDateTime createdAt;

    public Tag(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("태그 이름은 비어있을 수 없습니다.");
        }
        this.id = null;
        this.name = name;
        this.createdAt = null;
    }

    public Tag(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

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
