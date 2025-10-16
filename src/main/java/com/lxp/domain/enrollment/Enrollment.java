package com.lxp.domain.enrollment;

import com.lxp.domain.enrollment.enums.EnrollmentStatus;
import java.time.LocalDateTime;

public class Enrollment {
    private Long courseId;
    private Long studentId;
    private EnrollmentStatus status;
    private LocalDateTime enrollmentAt;
    private LocalDateTime updatedAt;

    public Enrollment() {}

    public Enrollment(Long courseId, Long studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public Enrollment(Long courseId, Long studentId,EnrollmentStatus status, LocalDateTime enrollmentAt, LocalDateTime updatedAt) {
        this(courseId,studentId);
        this.status = status;
        this.enrollmentAt = enrollmentAt;
        this.updatedAt = updatedAt;
    }

    public static Enrollment load(Long courseId, Long studentId, EnrollmentStatus status, LocalDateTime enrollmentAt, LocalDateTime updatedAt) {
       return new Enrollment(
              courseId, studentId, status, enrollmentAt, updatedAt);
    }

    public boolean isActive() {
        return status == EnrollmentStatus.ACTIVE;
    }

    public void cancel() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(enrollmentAt.plusDays(3))) {
            this.status = EnrollmentStatus.DEACTIVE;
        }
    }

    public EnrollmentView toView(){
        return new EnrollmentView(
                this.courseId, this.studentId, this.status, this.enrollmentAt, this.updatedAt
        );
    }
}
