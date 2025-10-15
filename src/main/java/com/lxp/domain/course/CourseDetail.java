package com.lxp.domain.course;

import java.util.Objects;
//VO 값 객체
public class CourseDetail {
    private final String content;
    private final String description;

    public CourseDetail(String content, String description) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("컨텐츠 이름은 비어있을 수 없습니다.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("상세설명은 비어있을 수 없습니다.");
        }
        this.content = content;
        this.description = description;
    }

    //getter로 내부 값을 외부로 알려주기만 할 뿐, setter 제공은 x
    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        //VO는 id 없음. 가지고 있는 값들이 모두 같으면 같은 객체로 본다. VO의 필수 요건.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDetail that = (CourseDetail) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        //equals를 재정의 했다면, hashCode도 반드시 재정의

        return Objects.hash(content, description);
    }

}
