package com.lxp.service;

import com.lxp.config.TransactionManager;
import com.lxp.infrastructure.dao.EnrollmentDao;
import java.sql.SQLException;

public class EnrollmentService {
    private final EnrollmentDao enrollmentDao;

    //    private final CourseDao courseDao ;
    //    private final UserDao userDao;

    public EnrollmentService(EnrollmentDao enrollmentDao
            //            CourseDao courseDao,
            //            UserDao userDao,
            ) {
        this.enrollmentDao = enrollmentDao;
        //        this.courseDao = courseDao;
        //        this.userDao = userDao;
    }

    public void saveEnroll(Long studentId, Long courseId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            //            User user = userDao.existById();
            //            Course course = courseDao.existById();

            TransactionManager.commit();

        } catch (Exception e) {
            TransactionManager.rollback();
            throw new RuntimeException("Enrollment failed", e);
        } finally {
            TransactionManager.close();
        }
    }
}
