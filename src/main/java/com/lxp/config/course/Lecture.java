// domain/course/Lecture.java
package com.lxp.config.course;

public class Lecture {
    private final Long id;          // 고유 식별자 (불변)
    private String title;           // 강의 이름
    private int lectureOrder;       // 강의 순서
    private String videoUrl;
    private int duration;           // 강의 시간 (분)

    public Lecture(Long id, String title, int lectureOrder, String videoUrl, int duration) {
        this.id = id;
        this.title = title;
        this.lectureOrder = lectureOrder;
        this.videoUrl = videoUrl;
        this.duration = duration;
    }

    // [강의 수정 기능]: 이름 변경
    public void rename(String newTitle) {
        this.title = newTitle;
    }

    // [강의 수정 기능]: 비디오 변경 시 시간 변경 규칙 적용
    public void changeVideo(String newUrl, int newDuration) {
        this.videoUrl = newUrl;
        this.duration = newDuration;
    }

    // [강의 수정 기능]: 순서 변경
    public void changeOrder(int newOrder) {
        this.lectureOrder = newOrder;
    }

    // 필수 Getter
    public Long getId() { return id; }
    public int getDuration() { return duration; }
    // ... (나머지 Getter 생략)
}