package com.lxp.infrastructure.row.course;

import java.sql.Timestamp;

public record SectionRow(
        Long sectionId,
        Long courseId,
        String name,
        int orderNum,
        Timestamp createdAt,
        Timestamp updatedAt) {}
