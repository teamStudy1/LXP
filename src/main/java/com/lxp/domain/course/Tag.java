package com.lxp.domain.course;

import java.util.Objects;
//엔티티
public class Tag {
    private long id;
    private String name;

    /**
     * 이름만으로 새로운 태그 만들 때 사용
     * @param name
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * 태그의 이름을 바꿀 때 사용
     */
    public void rename(String newName) {
        this.name = newName;
    }

    // 데이터 읽고, 쓰기 위한 기본 기능들
    public long getId() {
        return id;
    }


    public String getName() {
        return name;
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
