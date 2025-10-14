package com.lxp.api.controller;

import com.lxp.service.EnrollmentService;

public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    public void enroll(Long studentId, Long courseId) {
        // enrollmentService.saveEnroll(studentId, courseId);
    }
}
