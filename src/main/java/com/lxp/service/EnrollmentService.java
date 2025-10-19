package com.lxp.service;

import com.lxp.config.TransactionManager;
import com.lxp.infrastructure.dao.EnrollmentDao;
import java.sql.SQLException;

public class EnrollmentService {
    private final EnrollmentDao enrollmentDao;

    public EnrollmentService(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    public boolean enrollUserInCourse(Long userId, Long courseId) {
        try {

            TransactionManager.beginTransaction();

            enrollmentDao.save(userId, courseId);

            TransactionManager.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Database error during enrollment: " + e.getMessage());

            TransactionManager.rollback();

            throw new RuntimeException("Enrollment failed.", e);
        }
        // [FIXED] The 'finally' block that called TransactionManager.close() has been removed.
    }

    public boolean cancelEnrollment(Long userId, Long courseId) {
        try {
            TransactionManager.beginTransaction();
            int deletedRows = enrollmentDao.delete(userId, courseId);
            TransactionManager.commit();
            return deletedRows > 0;
        } catch (SQLException e) {
            System.err.println("Database error during enrollment cancellation: " + e.getMessage());
            TransactionManager.rollback();
            throw new RuntimeException("Enrollment cancellation failed.", e);
        }
    }
}