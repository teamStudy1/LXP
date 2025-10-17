package com.lxp.course.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// 엔티티
public class Section {
    private Long id;
    private String title;
    private int sectionOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<Lecture> lectures = new ArrayList<>();

    public Section(Long id,
                   String title,
                   int sectionOrder,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        validateTitle(title);
        this.id = id;
        this.title = title.trim();
        this.sectionOrder = sectionOrder;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
    }

    private Section(
            String title, int sectionOrder) {
        this.title = title;
        this.sectionOrder = sectionOrder;
    }

    public Lecture createLecture(String title, String videoUrl, int duration) {
        int nextOrder = lectures.stream().mapToInt(Lecture::getLectureOrder).max().orElse(0)+1;
        return createLectureAt(title, videoUrl, duration, nextOrder);
    }

    public Lecture createLectureAt(String title, String videoUrl, int duration, int nextOrder) {
        Lecture lec = new Lecture(
                title,
                nextOrder,
                videoUrl,
                duration
        );
        lectures.add(lec);
        return lec;
    }

    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    public void removeLecture(Long lectureId) {
        this.lectures.removeIf(l -> lectureId.equals(l.getId()));
    }

    public void rename(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public void changeOrder(int order) {
        this.sectionOrder = order;
}

    public void swapLectureOrder(Long lectureId1, Long lectureId2) {

        Lecture lec1 = findLectureById(lectureId1);
        Lecture lec2 = findLectureById(lectureId2);

        int order1 = lec1.getLectureOrder();
        int order2 = lec2.getLectureOrder();

        lec1.changeOrder(order2);
        lec2.changeOrder(order1);
    }


    public List<Lecture> getLectures() {
        return Collections.unmodifiableList(new ArrayList<>(lectures));
    }


    public int calculateSectionDuration() {
        return this.lectures.stream().mapToInt(Lecture::getDuration).sum();
    }

    private Lecture findLectureById(Long lectureId) {
        return lectures.stream()
                .filter(l -> Objects.equals(l.getId(), lectureId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다. id=" + lectureId));
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("섹션 제목은 비어있을 수 없습니다.");
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public int getSectionOrder() { return sectionOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}


