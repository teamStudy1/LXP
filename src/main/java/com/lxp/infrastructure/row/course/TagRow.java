package com.lxp.infrastructure.row.course;

import java.sql.Timestamp;

public record TagRow(
        Long tagId,
        String name,
        Timestamp createdAt
) {
}
