package com.lxp.domain.enrollment;

import com.lxp.domain.enrollment.enums.EnrollmentStatus;
import java.time.LocalDateTime;

public record EnrollmentView(
        Long courseId,
        Long studentId,
        EnrollmentStatus status,
        LocalDateTime enrollmentAt,
        LocalDateTime updatedAt
) {

}