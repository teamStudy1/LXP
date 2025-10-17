package com.lxp.course.persistence.row;

import java.sql.Timestamp;

public record SectionRow(
        Long sectionId,
        Long courseId,
        String name,
        int orderNum,
        Timestamp createdAt,
        Timestamp updatedAt) {}
