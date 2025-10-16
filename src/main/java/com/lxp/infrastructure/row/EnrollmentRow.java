package com.lxp.infrastructure.row;

import com.lxp.domain.enrollment.enums.EnrollmentStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record EnrollmentRow(
        Long userId,
        Long courseId,
        String status,
        Timestamp enrollmentAt,
        Timestamp updated_at
) {

}

