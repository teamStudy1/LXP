package com.lxp.domain.course;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Course {
    private Long id;
    private String title;
    private final Long instructorId;
    private String instructorName;
    private String content;
    private String contentDetail;
    private final Set<String> tags;
    private double totalTime;
    private int totalLectureCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Course(String title, Long instructorId, double totalTime, int totalLectureCount, String content, String contentDetail, LocalDateTime createdAt) {
        this.title = title;
        this.instructorId = instructorId;
        this.totalTime = totalTime;
        this.totalLectureCount = totalLectureCount;
        this.content = content;
        this.contentDetail = contentDetail;
        this.createdAt = createdAt;
        this.tags = new HashSet<>();
    }

    public Course(String title, Long instructorId, LocalDateTime createdAt) {
        this.title = title;
        this.instructorId = instructorId;
        this.createdAt = createdAt;
        this.tags = new HashSet<>();
    }


    public Course(
            Long id, String title, Long instructorId, String instructorName, String content, String contentDetail,
            Set<String> tags,
            double totalTime, int totalLectureCount,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.content = content;
        this.contentDetail = contentDetail;
        this.tags = tags;
        this.totalTime = totalTime;
        this.totalLectureCount = totalLectureCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Long getInstructorId() { return instructorId; }
    public String getInstructorName() { return instructorName; }
    public String getContent() { return content; }
    public String getContentDetail() { return contentDetail; }
    public Set<String> getTags() { return tags; }
    public double getTotalTime() { return totalTime; }
    public int getTotalLectureCount() { return totalLectureCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}