package com.lxp.api.dto;

import java.time.LocalDateTime;

    public record CourseView(
            Long id,
            String title,
            Long instructorId,
            LocalDateTime createdAt
    ) {

    }
