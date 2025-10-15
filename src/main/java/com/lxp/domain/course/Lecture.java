package com.lxp.domain.course;

import java.time.LocalDateTime;
//엔티티
public class Lecture {
    private final Long id;
    private String name;
    private int seconds; //이 강의가 몇 초짜리 인지 길이 저장하는 변수(초 단위)
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Lecture(String name, int seconds) {
        validateName(name);
        validateSeconds(seconds);
        this.id = null;
        this.name = name;
        this.seconds = seconds;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Lecture(Long id, String name, int seconds, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.seconds = seconds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    /**
     * 재생 시간을 변경하는 기능
     */
    public void changeDuration(int newSeconds) {
        validateSeconds(newSeconds);
        this.seconds = newSeconds;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("강의 이름은 비어있을 수 없습니다.");
        }
    }

    private void validateSeconds(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("강의 시간은 음수일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeconds() {
        return seconds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
