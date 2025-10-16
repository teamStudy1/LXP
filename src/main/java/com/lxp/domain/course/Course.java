package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.*;

//엔티티
public class Course {
    private Long id;
    private String title;
    private final Long instructorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private CourseDetail detail;
    private final Set<Tag> tags;
    private final List<Section> sections;
    private int totalSeconds;


    public Course(String title, Long instructorId, LocalDateTime createdAt, List<Section> sections, Set<Tag> tags) {
        this.title = title;
        this.instructorId = instructorId;
        this.createdAt = createdAt;
        this.sections = sections != null ? sections : new ArrayList<Section>();
        this.tags = tags != null ? tags : new HashSet<Tag>();
        updateTotalDuration();
    }


    public Course(Long id, String title, Long instructorId, List<Section> sections, Set<Tag> tags, LocalDateTime createdAt) {
        this(title, instructorId, createdAt, sections, tags);
        this.id = id;
        this.createdAt = createdAt;
        updateTotalDuration();
    }

    public void rename(String newTitle) {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("강좌 이름은 필수입니다.");
        }
        this.title = newTitle;
    }



    private void updateTotalDuration() {
        this.totalSeconds = this.sections.stream()
                .mapToInt(Section::calculateSectionDuration)
                .sum();
    }

    public void removeSection(Section section) {
        this.sections.remove(section);
        updateTotalDuration();
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getInstructorId() {
        return instructorId;
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

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
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