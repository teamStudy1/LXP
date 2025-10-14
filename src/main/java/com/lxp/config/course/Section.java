// domain/course/Section.java
package com.lxp.config.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Section {
    private final Long id;
    private String title;
    private int sectionOrder;
    private final List<Lecture> lectures = new ArrayList<>(); // Composition

    public Section(Long id, String title, int sectionOrder) {
        this.id = id;
        this.title = title;
        this.sectionOrder = sectionOrder;
    }

    // [섹션 수정 기능]: 이름 변경
    public void rename(String newTitle) {
        this.title = newTitle;
    }

    // [섹션 수정 기능]: 순서 변경
    public void changeOrder(int newOrder) {
        this.sectionOrder = newOrder;
    }

    // 강의 추가
    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    // 강의 삭제 (Lecture Entity 관리)
    public void removeLecture(Long lectureId) {
        this.lectures.removeIf(l -> l.getId().equals(lectureId));
    }

    // 섹션 자체의 총 시간 계산 (Lecture의 duration 합산)
    public int calculateSectionDuration() {
        return this.lectures.stream().mapToInt(Lecture::getDuration).sum();
    }

    // 컬렉션 불변 처리: 외부 직접 수정 방지
    public List<Lecture> getLectures() {
        return Collections.unmodifiableList(lectures);
    }

    public Long getId() {
        return id;
    }


}