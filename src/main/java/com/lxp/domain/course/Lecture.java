package com.lxp.domain.course;

import java.time.LocalDateTime;
//엔티티
public class Lecture {
    private final Long id;
    private String title;
    private int lectureOrder;
    private String videoUrl;
    private int duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Lecture(Long id, String title, int lectureOrder, String videoUrl, int duration,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.lectureOrder = lectureOrder;
        this.videoUrl = videoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void rename(String newName) {
        validateName(newName);
        this.title = newName;
    }


    public void changeVideo(String newUrl, int newDuration) {
        this.videoUrl = newUrl;
        this.duration = newDuration;
    }

    private void validateName(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("강의 이름은 비어있을 수 없습니다.");
        }
    }

    public void changeOrder(int newOrder) {
        this.lectureOrder = newOrder;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getLectureOrder() {
        return lectureOrder;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
