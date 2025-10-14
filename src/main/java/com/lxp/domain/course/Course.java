package com.lxp.domain.course;

import java.time.Instant;
import java.util.*;


public class Course {
    private long id;
    private String title;
    private String instructor;
    private Instant createdAt;
    private Instant updatedAt;

    private CourseDetail detail;
    private Set<Tag> tags = new HashSet<>();
    private List<Section> sections = new ArrayList<>();


    public Course(String title, String instructor) {
        this.title = title;
        this.instructor = instructor;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


    public void addTags(List<Tag> tagList) {
        this.tags.addAll(tagList);
        this.updatedAt = Instant.now();
    }

    public void removeTags(List<Tag> tagList) {
        this.tags.removeAll(tagList);
        this.updatedAt = Instant.now();
    }

    public void rename(String newTitle) {
        this.title = newTitle;
        this.updatedAt = Instant.now();
    }

    public void changeDetail(CourseDetail newDetail) {
        this.detail = newDetail;
        if (newDetail != null) {
            newDetail.setCourse(this);
        }
        this.updatedAt = Instant.now();
    }

    public void addSection(Section section) {
        this.sections.add(section);
        section.setCourse(this);
        this.updatedAt = Instant.now();
    }

    public int getTotalSeconds() {
        return this.sections.stream()
                .mapToInt(Section::calculateSectionDuration)
                .sum();
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

    public String getInstructor() {
        return instructor;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public CourseDetail getCourseDetail() {
        return detail;
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
