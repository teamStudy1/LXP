package com.lxp.course.persistence.row;

import java.sql.Timestamp;

public record CourseRow(
        Long courseId,
        String title,
        Long instructorId,
        Long categoryId,
        String totalTime,
        int totalLectureCount,
        String content,
        String contentDetail,
        Timestamp createdAt,
        Timestamp updatedAt) {}
