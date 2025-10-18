package com.lxp.api.dto;

import java.time.LocalDateTime;

public record CourseView(
        Long id,
        String title,
        String instructorName,
        String content,
        String contentDetail,
        double totalTime,
        int totalLectureCount,
        LocalDateTime createdAt
) {
}