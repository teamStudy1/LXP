package com.lxp.infrastructure.row;

import java.sql.Timestamp;

public record CategoryRow(
        Long categoryId,
        String name,
        Long parentId,
        Timestamp createdAt,
        Timestamp updatedAt
) {
}