package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.*;

//엔티티
public class Course {
    private final Long id;
    private String title;
    private String instructor;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private CourseDetail detail;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Section> sections = new ArrayList<>();


    public Course(String title, String instructor) {
        this.id = null;
        this.title = title;
        this.instructor = instructor;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Course(Long id, String title, String instructor, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void rename(String newTitle) {
        this.title = newTitle;
    }

    public void changeDetail(CourseDetail newDetail) {
        this.detail = newDetail;
    }

    public void addTags(Collection<Tag> tagToAdd) {
        this.tags.addAll(tagToAdd);
    }

    public void removeTags(Collection<Tag> tagToRemove) {
        this.tags.removeAll(tagToRemove);
    }

    public void addSection(Section section) {
        this.sections.add(section);
        section.setCourse(this);
    }
    public void removeSection(Section section) {
        this.sections.remove(section);
        section.setCourse(null);
    }

    public void clearTags() {
        this.tags.clear();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructor() {
        return instructor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public CourseDetail getCourseDetail() {
        return detail;
    }

    /**
     * 외부에서 sections 리스트를 맘대로 못바꾸게
     */
    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

}
