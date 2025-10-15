package com.lxp.domain.course;

import java.util.*;

//엔티티
public class Course {
    private long id;
    private String title; //강의 제목
    private long instructorId;

    private CourseDetail detail; //강의 상세 설명
    private Set<Tag> tags = new HashSet<>(); //  태그 중복 x 위해
    private List<Section> sections = new ArrayList<>(); // 강의 섹션들 순서가 중요


    public Course(String title, long instructorId) {
        this.title = title;
        this.instructorId = instructorId;
    }

    // 여러 개 태그 한번에 추가
    public void addTags(List<Tag> tagList) {
        this.tags.addAll(tagList);
    }

    // 여러 개 태그 한번에 제거
    public void removeTags(List<Tag> tagList) {
        this.tags.removeAll(tagList);
    }

    public void rename(String newTitle) {
        this.title = newTitle;
    }

    public void changeDetail(CourseDetail newDetail) {
        this.detail = newDetail;
    }

    public void addSection(Section section) {
        this.sections.add(section);
        section.setCourse(this);
    }
    public void removeSection(Section section) {
        this.sections.remove(section);
        section.setCourse(null);
    }

    public int getTotalSeconds() {
        return this.sections.stream() // section 리스트를 stream으로 바꿈
                .mapToInt(Section::calculateSectionDuration) //각 section 객체에 총 재생시간 계산하라 요청
                .sum(); // 그걸 더함
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public long getInstructorId() {
        return instructorId;
    }

    public CourseDetail getCourseDetail() {
        return detail;
    }

    /**
     * 외부에서 sections 리스트를 맘대로 못바꾸게
     * @return
     */
    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

}
