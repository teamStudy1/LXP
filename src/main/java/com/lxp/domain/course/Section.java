package com.lxp.domain.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Section {
    private long id;
    private String name;
    private Course course; // 내가 어떤 강의에 속해있는지 알려주는 정보 (역방향)
    private List<Lecture>  lectures = new ArrayList<>(); // 이 섹션에 포함된 강의 영상


    public Section(String name) {
        this.name = name;
    }

    /**
     * 이 강의들의 총 재생 시간을 계산하는 기능
     * @return 포함된 모든 영상의 재생 시간을 합한 값
     */
    public int calculateSectionDuration() {
        // lectures 리스트에 있는 모든 Lecture 객체들의 seconds 값을 다 더해서 알려줘!
        return this.lectures.stream()
                .mapToInt(Lecture::getSeconds)
                .sum();
    }


    /**
     * 여러 개의 강의 영상을 한번에 추가하는 기능
     */
    public void addLectures(List<Lecture> lectureList) {
        this.lectures.addAll(lectureList);
    }

    public void rename(String newName) {
        this.name = newName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
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
