package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//엔티티
public class Section {
    private final Long id;
    private String name;
    private Course course;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<Lecture> lectures = new ArrayList<>();


    public Section(String name) {
        validateName(name);
        this.id = null;
        this.name = name;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Section(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 이 강의들의 총 재생 시간을
     * 계산하는 기능
     * @return 포함된 모든 영상의 재생 시간을 합한 값
     */
    public int calculateSectionDuration() {
        // lectures 리스트에 있는 모든 Lecture 객체들의 seconds 값을 다 더해서 알려줘!
        return this.lectures.stream()
                .mapToInt(Lecture::getSeconds)
                .sum();
    }


    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("강의 이름은 비어있을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


}
