package com.lxp.domain.course;


import java.time.LocalDateTime;

public class Lecture {
    private final Long id;
    private String name;
    private int seconds; //이 강의가 몇 초짜리 인지 길이 저장하는 변수(초 단위)
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Lecture(String name, int seconds) {
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

    /**
     * 이름을 변경하는 기능
     * @param newName
     */
    public void rename(String newName) {
        this.name = newName;
    }

    /**
     * 재생 시간을 변경하는 기능
     */
    public void changeDuration(int newSeconds) {
        this.seconds = newSeconds;
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
