package com.lxp.infrastructure.row.course;

import java.sql.Timestamp;

public record CourseRow(
        Long courseId,
        String title,
        Long instructorId,
        String instructorName,
        String content,
        String contentDetail,
        String tags,
        Long categoryId,
        double totalTime,
        int totalLectureCount,
        Timestamp createdAt,
        Timestamp updatedAt) {
}