package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.*;

public class Course {
    private Long id;
    private String title;
    private final Long instructorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CourseDetail detail;
    private final Set<Tag> tags;
    private final List<Section> sections;
    private double totalSeconds;

    public Course(
            String title,
            Long instructorId,
            CourseDetail detail,
            LocalDateTime createdAt,
            List<Section> sections,
            Set<Tag> tags) {
        this.title = title;
        this.detail = detail;
        this.instructorId = instructorId;
        this.createdAt = createdAt;
        this.sections = sections != null ? sections : new ArrayList<Section>();
        this.tags = tags != null ? tags : new HashSet<Tag>();
        updateTotalDuration();
    }

    public Course(Long id, String title, Long instructorId, LocalDateTime createdAt, LocalDateTime updatedAt, CourseDetail detail, Set<Tag> tags, List<Section> sections) {
        this(title, instructorId, detail,  createdAt, sections, tags);
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        updateTotalDuration();
    }

    public void rename(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("강좌 이름은 필수입니다.");
        }
        this.title = title;
    }

    public void setDetail(CourseDetail detail) {
        if (detail == null) throw new IllegalArgumentException("강좌 상세는 필수입니다.");
        this.detail = detail;
    }


    public void addSection(Section section) {
        Objects.requireNonNull(section, "section은 null일 수 없습니다.");

        int nextOrder = sections.stream()
                .mapToInt(Section::getSectionOrder)
                .max()
                .orElse(0) + 1;
        section.changeOrder(nextOrder);
        sections.add(section);
        updateTotalDuration();
    }



    public void removeSectionById(Long sectionId) {
        boolean removed = this.sections.removeIf(s -> Objects.equals(s.getId(), sectionId));
        if (removed) {
            updateTotalDuration();
        }
    }

    public void removeSection(Section section) {
        if (this.sections.remove(section)) {
            updateTotalDuration();
        }
    }

    public void addTag(Tag tag) {
        Objects.requireNonNull(tag, "태그는 null일 수 없습니다.");
        if (this.tags.add(tag)) {
        }
    }


    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    private void updateTotalDuration() {
        this.totalSeconds = this.sections.stream()
                .mapToInt(Section::calculateSectionDuration)
                .sum();
    }



    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Long getInstructorId() { return instructorId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public CourseDetail getCourseDetail() { return detail; }
    public double getTotalSeconds() { return totalSeconds; }


    public List<Section> getSections() {
        return Collections.unmodifiableList(new ArrayList<>(sections));
    }
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(new HashSet<>(tags));
    }

}
