// domain/course/Course.java
package com.lxp.config.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Course {
    private Long id;
    private String title;
    private final Long instructorId;

    private final List<Section> sections; // Section 관리 책임 .    // 왜 List를 불변으로 두었는가? why?
    private final Set<Tag> tags;           // Tag VO 관리 책임
    private int totalSeconds;              // 강좌의 총 시간 (파생 값)

    public Course(Long id, String title, Long instructorId, Set<Tag> tags, List<Section> sections) {
        this(title,instructorId,sections,tags);
        this.id = id;
        updateTotalDuration(); // 생성 시 총 시간 계산
    }

    public Course(String title, Long instructorId, List<Section> sections, Set<Tag> tags) {
        this.title = title;
        this.instructorId = instructorId;
        this.sections = sections != null ? sections : new ArrayList<Section>();
        this.tags = tags;
        updateTotalDuration();
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
        updateTotalDuration();
    }

    // [핵심 관리 책임]: 강좌의 총 시간 업데이트
    private void updateTotalDuration() {
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

    public List<Section> getSections() {
        return sections;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    // ... (Section, Tag 등 컬렉션 불변 Getter 및 id, instructorId Getter 생략)


    // 총 시간을 "X시간 Y분" 형태로 표시
    public String getFormattedDuration() {
        // 1. totalSeconds 값을 초단위로.
        int totalSeconds = this.totalSeconds;

        // 2. 시간 계산 (3600초 = 1시간)
        int hours = totalSeconds / 3600;

        // 3. 남은 초 계산 (나머지 연산)
        int remainingSeconds = totalSeconds % 3600;

        // 4. 분 계산 (60초 = 1분)
        int minutes = remainingSeconds / 60;

        // 5. 문자열로 조합하여 반환
        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours).append("시간 ");
        }

        // 분이 0보다 크거나, 시간이 0이고 분이라도 1분 이상일 경우에만 분을 표시
        if (minutes > 0 || (hours == 0 && totalSeconds > 0)) {
            sb.append(minutes).append("분");
        }

        // 총 시간이 0초일 경우 (빈 강좌)
        if (sb.isEmpty()) {
            return "0";
        }

        return sb.toString().trim();
    }

// ... (나머지 Getter)
}