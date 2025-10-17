package com.lxp.infrastructure.row.course;

import java.sql.Timestamp;

public record CourseRow(
        Long courseId,
        String title,
        Long instructorId,
        Long categoryId,
        int totalTime,
        int totalLectureCount,
        String content,
        String contentDetail,
        Timestamp createdAt,
        Timestamp updatedAt) {
}
