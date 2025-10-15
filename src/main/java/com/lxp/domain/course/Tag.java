// Tag.java 파일의 최종 내용

package com.lxp.domain.course; // 👈 1. 파일의 실제 경로에 맞는 패키지 선언

import java.util.Objects;

public final class Tag { // 👈 2. 파일 이름과 동일한 public 클래스 선언
    private final String name;

    public Tag(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("태그 이름은 필수입니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // VO 핵심: 값이 같으면 같은 객체
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