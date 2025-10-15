package com.lxp.domain.course;

import java.util.Objects;
//VO 벨류 오브젝트
public class CourseDetail {
    private final String content;
    private final String description;

    public CourseDetail(String content, String description) {
        this.content = content;
        this.description = description;
    }


    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        //VO는 id 없음. 가지고 있는 값들이 모두 같으면 같은 객체로 본다.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDetail that = (CourseDetail) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        //equals를 재정의 했다면, hashCode도 반드시 재정의
        // 같은 값을 가진 객체는 같은 해시코드를 반환해야 한다는 규칙 때문임
        return Objects.hash(content, description);
    }

}
