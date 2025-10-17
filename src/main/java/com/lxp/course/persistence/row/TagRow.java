package com.lxp.course.persistence.row;

import java.sql.Timestamp;

public record TagRow(
        Long tagId,
        String name,
        Timestamp createdAt
) {
}
