package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//엔티티
public class Section {
    private final Long id;
    private String title;
    private final int sectionOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<Lecture> lectures = new ArrayList<>();


    public Section(Long id, String title, int sectionOrder, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.sectionOrder = sectionOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public void swapLectureOrder(Long lectureId1, Long lectureId2) {

        Lecture lec1 = findLectureById(lectureId1);
        Lecture lec2 = findLectureById(lectureId2);


        int order1 = lec1.getLectureOrder();
        int order2 = lec2.getLectureOrder();

        lec1.changeOrder(order2);
        lec2.changeOrder(order1);

    }

    public void insertNewLecture(Lecture newLecture, int targetOrder) {

        for (Lecture lecture : lectures) {
            if (lecture.getLectureOrder() < targetOrder) {
                continue;
            }
            lecture.changeOrder(lecture.getLectureOrder() + 1);
        }

        newLecture.changeOrder(targetOrder);
        this.lectures.add(newLecture);

    }

    public int calculateSectionDuration() {
        return this.lectures.stream().mapToInt(Lecture::getDuration).sum();
    }


    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("강의 이름은 비어있을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    /**
     * 강의 목록을 외부에서 볼 수 있게 해주지만,
     * 외부에서 마음대로 추가하거나 삭제 못하게 읽기 전용으로 포장해서 줌
     */
    public List<Lecture> getLectures() {
        return Collections.unmodifiableList(lectures);
    }

    private Lecture findLectureById(Long lectureId) {
        return lectures.stream()
                .filter(l -> l.getId().equals(lectureId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));
    }


}
