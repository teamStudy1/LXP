package com.lxp.course.domain.model;

import java.time.LocalDateTime;

// 엔티티
public class Lecture {
    private Long id;
    private String title;
    private int lectureOrder;
    private String videoUrl;
    private int duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Lecture(Long id,
                    String title,
                    int lectureOrder,
                    String videoUrl,
                    int duration,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this(title,lectureOrder,videoUrl,duration);

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Lecture(
                    String title,
                    int lectureOrder,
                    String videoUrl,
                    int duration) {
        validateName(title);
        validateOrder(lectureOrder);
        validateUrl(videoUrl);
        validateDuration(duration);

        this.title = title.trim();
        this.lectureOrder = lectureOrder;
        this.videoUrl = videoUrl.trim();
        this.duration = duration;
    }


    public void rename(String newName) {
        validateName(newName);
        this.title = newName;
    }

    public void changeVideo(String newUrl, int newDuration) {
        validateUrl(newUrl);
        validateDuration(newDuration);
        this.videoUrl = newUrl.trim();
        this.duration = newDuration;
    }

    public void changeOrder(int newOrder) {
        validateOrder(newOrder);
        this.lectureOrder = newOrder;
    }

    private void validateName(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("강의 이름은 비어있을 수 없습니다.");
        }
    }


    private void validateOrder(int order) {
        if (order < 1) throw new IllegalArgumentException("lectureOrder는 1 이상이어야 합니다.");
    }

    private void validateUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("videoUrl은 비어있을 수 없습니다.");
        }
    }

    private void validateDuration(int duration) {
        if (duration < 0) throw new IllegalArgumentException("duration은 0 이상이어야 합니다.");
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
