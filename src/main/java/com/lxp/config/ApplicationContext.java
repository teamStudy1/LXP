package com.lxp.config;

import com.lxp.api.controller.CourseController;
import com.lxp.api.controller.EnrollmentController;
import com.lxp.api.controller.UserController;
import com.lxp.handler.CourseHandler;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;
import com.lxp.infrastructure.dao.*;
import com.lxp.service.CourseService;
import com.lxp.service.EnrollmentService;
import com.lxp.service.UserService;
import com.lxp.util.CLIRouter;
import javax.sql.DataSource;

public class ApplicationContext {
    private ApplicationContext() {}

    private static class DataSourceHolder {
        private static final DataSource INSTANCE = DataSourceFactory.getDataSource();
    }
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }


    private static class CourseDaoHolder {
        private static final CourseDao INSTANCE = new JdbcCourseDao(getDataSource());
    }
    private static class CourseDetailDaoHolder {
        private static final CourseDetailDao INSTANCE = new JdbcCourseDetailDao();
    }
    private static class EnrollmentDaoHolder {
        private static final EnrollmentDao INSTANCE = new EnrollmentDao();
    }
    private static class UserDaoHolder {
        private static final UserDao INSTANCE = new UserDao();
    }
    public static CourseDao getCourseDao() { return CourseDaoHolder.INSTANCE; }
    public static CourseDetailDao getCourseDetailDao() { return CourseDetailDaoHolder.INSTANCE; }
    public static EnrollmentDao getEnrollmentDao() { return EnrollmentDaoHolder.INSTANCE; }
    public static UserDao getUserDao() { return UserDaoHolder.INSTANCE; }


    private static class CourseServiceHolder {
        private static final CourseService INSTANCE = new CourseService(getCourseDao(), getCourseDetailDao());
    }
    private static class EnrollmentServiceHolder {
        private static final EnrollmentService INSTANCE = new EnrollmentService(getEnrollmentDao());
    }
    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService(getUserDao());
    }
    public static CourseService getCourseService() { return CourseServiceHolder.INSTANCE; }
    public static EnrollmentService getEnrollmentService() { return EnrollmentServiceHolder.INSTANCE; }
    public static UserService getUserService() { return UserServiceHolder.INSTANCE; }


    private static class CourseControllerHolder {
        private static final CourseController INSTANCE = new CourseController(getCourseService());
    }
    private static class EnrollmentControllerHolder {
        private static final EnrollmentController INSTANCE = new EnrollmentController(getEnrollmentService());
    }
    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController(getUserService());
    }
    public static CourseController getCourseController() { return CourseControllerHolder.INSTANCE; }
    public static EnrollmentController getEnrollmentController() { return EnrollmentControllerHolder.INSTANCE; }
    public static UserController getUserController() { return UserControllerHolder.INSTANCE; }


    private static class CourseHandlerHolder {
        private static final CourseHandler INSTANCE = new CourseHandler(getCourseController());
    }
    private static class EnrollmentHandlerHolder {
        private static final EnrollmentHandler INSTANCE = new EnrollmentHandler(getEnrollmentController());
    }
    private static class UserHandlerHolder {
        private static final UserHandler INSTANCE = new UserHandler(getUserController());
    }
    public static CourseHandler getCourseHandler() { return CourseHandlerHolder.INSTANCE; }
    public static EnrollmentHandler getEnrollmentHandler() { return EnrollmentHandlerHolder.INSTANCE; }
    public static UserHandler getUserHandler() { return UserHandlerHolder.INSTANCE; }


    private static class RouterHolder {
        private static final CLIRouter INSTANCE = new CLIRouter(getCourseHandler(), getEnrollmentHandler(), getUserHandler());
    }
    public static CLIRouter getRouter() {
        return RouterHolder.INSTANCE;
    }
}