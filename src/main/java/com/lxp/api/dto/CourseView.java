package com.lxp.api.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record CourseView(
        Long id,
        String title,
        String instructorName,
        String content,
        String contentDetail,
        Set<String> tags,
        double totalTime,
        int totalLectureCount,
        LocalDateTime createdAt
) {
}