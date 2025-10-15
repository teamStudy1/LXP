package com.lxp.domain.enrollment;

import com.lxp.domain.enrollment.enums.EnrollmentStatus;
import java.time.LocalDateTime;

public class Enrollment {
    private Long courseId;
    private Long studentId;
    private EnrollmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Enrollment() {}

    public Enrollment(Long courseId, Long studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public boolean isActive() {
        return status == EnrollmentStatus.ACTIVE;
    }

    public void cancel() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(createdAt.plusDays(3))) {
            this.status = EnrollmentStatus.DEACTIVE;
        }
    }
}
