package com.lxp.service;

import com.lxp.config.TransactionManager;
import com.lxp.domain.enrollment.Enrollment;
import com.lxp.infrastructure.dao.EnrollmentDao;
import com.lxp.infrastructure.mapper.EnrollmentMapper;
import com.lxp.infrastructure.row.EnrollmentRow;

import java.sql.SQLException;

public class EnrollmentService {
    private final EnrollmentDao enrollmentDao;
        private final CourseDao courseDao ;
        private final UserDao userDao;

    public EnrollmentService(EnrollmentDao enrollmentDao
                        CourseDao courseDao,
                        UserDao userDao) {
                this.enrollmentDao = enrollmentDao;
                this.courseDao = courseDao;
                this.userDao = userDao;
    }

    public void saveEnroll(Long studentId, Long courseId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            if (!userDAO.existsById(userId)) {
                throw new IllegalArgumentException("존재하지 않는 사용자");
            }

            if (!courseDAO.existsById(courseId)) {
                throw new IllegalArgumentException("존재하지 않는 강의");
            }

            if (enrollmentDAO.exists(userId, courseId)) {
                throw new IllegalArgumentException("이미 수강 중");
            }

            Enrollment enrollment = new Enrollment(user.id, course.id);
            EnrollmentRow row = EnrollmentMapper.toRow(enrollment.toView());
            enrollmentDAO.save(row);





            TransactionManager.commit();

        } catch (Exception e) {
            TransactionManager.rollback();
            throw new RuntimeException("Enrollment failed", e);
        } finally {
            TransactionManager.close();
        }
    }
}
