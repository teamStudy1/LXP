package com.lxp.domain.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Section {
    private long id;
    private String name;
    private Course course;
    private List<Lecture>  lectures = new ArrayList<>();

    public Section(String name) {
        this.name = name;
    }

    public int calculateSectionDuration() {
        return this.lectures.stream()
                .mapToInt(lecture -> (int) lecture.getSeconds())
                .sum();
    }

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
