// domain/course/Course.java
package com.lxp.config.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Course {
    private final Long id;
    private String title;
    private final Long instructorId;

    private final List<Section> sections; // Section 관리 책임
    private final Set<Tag> tags;           // Tag VO 관리 책임
    private int totalSeconds;              // 강좌의 총 시간 (파생 값)

    public Course(Long id, String title, Long instructorId, Set<Tag> tags, List<Section> sections) {
        this.id = id;
        this.title = title;
        this.instructorId = instructorId;
        this.tags = tags;
        this.sections = sections != null ? sections : new ArrayList<>();
        this.updateTotalDuration(); // 생성 시 총 시간 계산
    }

    // [강좌 수정 기능]: 이름 변경 (요구사항 반영)
    public void renameTitle(String newTitle) {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("강좌 이름은 필수입니다.");
        }
        this.title = newTitle;
    }

    // [섹션 등록 기능]: AR을 통해 Section Entity 추가
    public void addSection(Section newSection) {
        this.sections.add(newSection);
        this.updateTotalDuration();
    }

    // [핵심 관리 책임]: 강좌의 총 시간 업데이트
    public void updateTotalDuration() {
        this.totalSeconds = this.sections.stream()
                .mapToInt(Section::calculateSectionDuration)
                .sum();
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public Long getId() {
        return id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    // ... (Section, Tag 등 컬렉션 불변 Getter 및 id, instructorId Getter 생략)
}