package com.lxp.service;

import com.lxp.infrastructure.dao.EnrollmentDao;

public class EnrollmentService {
    private final EnrollmentDao enrollmentDao;

    public EnrollmentService(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = new EnrollmentDao();
    }

    public void saveEnroll(Long studentId, Long courseId) {
    }
}
