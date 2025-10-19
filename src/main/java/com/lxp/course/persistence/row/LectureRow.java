package com.lxp.course.persistence.row;

import java.sql.Timestamp;

public record LectureRow(
        Long lectureId,
        Long sectionId,
        String name,
        int lectureTime,
        int orderNum,
        String videoUrl,
        Timestamp createdAt,
        Timestamp updatedAt) {}
