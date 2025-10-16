package com.lxp.config;

import com.lxp.api.controller.CourseController;
import com.lxp.api.controller.EnrollmentController;
import com.lxp.handler.CourseHandler;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.infrastructure.dao.CourseDao;
import com.lxp.infrastructure.dao.EnrollmentDao;
import com.lxp.service.CourseService;
import com.lxp.service.EnrollmentService;
import com.lxp.util.CLIRouter;
import javax.sql.DataSource;

public class ApplicationContext {
    private ApplicationContext() {}

    private static class EnrollmentDaoHolder {
        private static final EnrollmentDao INSTANCE = new EnrollmentDao();
    }

    private static class EnrollmentServiceHolder {
        private static final EnrollmentService INSTANCE = new EnrollmentService(getEnrollmentDao());
    }

    private static class EnrollmentControllerHolder {
        private static final EnrollmentController INSTANCE =
                new EnrollmentController(getEnrollmentService());
    }

    private static class EnrollmentHandlerHolder {
        private static final EnrollmentHandler INSTANCE =
                new EnrollmentHandler(getEnrollmentController());
    }

    public static EnrollmentHandler getEnrollmentHandler() {
        return EnrollmentHandlerHolder.INSTANCE;
    }

    public static EnrollmentDao getEnrollmentDao() {
        return EnrollmentDaoHolder.INSTANCE;
    }

    public static EnrollmentService getEnrollmentService() {
        return EnrollmentServiceHolder.INSTANCE;
    }

    public static EnrollmentController getEnrollmentController() {
        return EnrollmentControllerHolder.INSTANCE;
    }

    private static class CourseDaoHolder {
        private static final CourseDao INSTANCE = new CourseDao(getDataSource());
    }

    private static class CourseServiceHolder {
        private static final CourseService INSTANCE = new CourseService(getCourseDao());
    }

    private static class CourseControllerHolder {
        private static final CourseController INSTANCE = new CourseController(getCourseService());
    }

    private static class CourseHandlerHolder {
        private static final CourseHandler INSTANCE = new CourseHandler(getCourseController());
    }

    public static CourseDao getCourseDao() {
        return CourseDaoHolder.INSTANCE;
    }

    public static CourseService getCourseService() {
        return CourseServiceHolder.INSTANCE;
    }

    public static CourseController getCourseController() {
        return CourseControllerHolder.INSTANCE;
    }

    public static CourseHandler getCourseHandler() {
        return CourseHandlerHolder.INSTANCE;
    }

    private static class RouterHolder {
        private static final CLIRouter INSTANCE =
                new CLIRouter(getEnrollmentHandler(), getCourseHandler());
    }

    public static CLIRouter getRouter() {
        return RouterHolder.INSTANCE;
    }

    private static class DataSourceHolder {
        private static final DataSource INSTANCE = DataSourceFactory.getDataSource();
    }

    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }
}
