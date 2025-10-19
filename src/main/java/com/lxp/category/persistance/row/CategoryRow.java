package com.lxp.category.persistance.row;

import java.sql.Timestamp;

public record CategoryRow(
        Long categoryId,
        String name,
        Long parentId,
        int depth,
        Timestamp createdAt,
        Timestamp updatedAt) {}
