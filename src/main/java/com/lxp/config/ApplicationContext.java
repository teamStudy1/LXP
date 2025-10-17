package com.lxp.config;

import com.lxp.course.persistence.dao.CourseDao;
import com.lxp.course.persistence.dao.LectureDao;
import com.lxp.course.persistence.dao.SectionDao;
import com.lxp.course.persistence.dao.TagDao;
import com.lxp.course.web.CourseController;
import com.lxp.api.controller.EnrollmentController;
import com.lxp.api.controller.UserController;
import com.lxp.handler.CourseHandler;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;
import com.lxp.infrastructure.dao.enrollment.EnrollmentDao;
import com.lxp.infrastructure.dao.UserDao;
import com.lxp.course.persistence.JdbcCourseRepository;
import com.lxp.course.service.CourseService;
import com.lxp.service.EnrollmentService;
import com.lxp.service.UserService;
import com.lxp.handler.CLIRouter;
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

    // user component
    private static class UserDaoHolder {
        private static final UserDao INSTANCE = new UserDao();
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService(getUserDao());
    }

    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController(getUserService());
    }

    private static UserDao getUserDao() {
        return UserDaoHolder.INSTANCE;
    }

    private static UserService getUserService() {
        return UserServiceHolder.INSTANCE;
    }

    private static UserController getUserController() {
        return UserControllerHolder.INSTANCE;
    }

    // handleController component
    private static class HandleControllerHolder {
        private static final EnrollmentHandler INSTANCE =
                new EnrollmentHandler(getEnrollmentController());
    }

    public static EnrollmentHandler getHandleController() {
        return HandleControllerHolder.INSTANCE;
    }

    private static class UserHandlerHolder {
        private static final UserHandler INSTANCE = new UserHandler(getUserController());
    }

    public static UserHandler getUserHandler() {
        return UserHandlerHolder.INSTANCE;
    }

    // course component
    private static class CourseDaoHolder {
        private static final CourseDao INSTANCE = new CourseDao(getDataSource());
    }

    private static class CourseServiceHolder {
        private static final CourseService INSTANCE = new CourseService(getJdbcCourseRepository()
        );
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


    // sections
    public static class SectionDaoHolder {
        private static final SectionDao INSTANCE = new SectionDao(getDataSource());
    }

    public static SectionDao getSectionDao() {
        return SectionDaoHolder.INSTANCE;
    }


    // lecture
    public static class LectureDaoHolder {
        private static final LectureDao INSTANCE = new LectureDao(getDataSource());
    }

    public static LectureDao getLectureDao() {
        return LectureDaoHolder.INSTANCE;
    }


    //tag
    public static class TagDaoHolder {
        private static final TagDao INSTANCE = new TagDao(getDataSource());
    }

    public static TagDao getTagDao() {
        return TagDaoHolder.INSTANCE;
    }


    public static class JdbcCourseRepositoryHolder {
        private static final JdbcCourseRepository INSTANCE = new JdbcCourseRepository(
                getCourseDao(),
                getSectionDao(),
                getLectureDao(),
                getTagDao()
        );
    }

    public static JdbcCourseRepository getJdbcCourseRepository() {
        return JdbcCourseRepositoryHolder.INSTANCE;
    }






    private static class RouterHolder {
        private static final CLIRouter INSTANCE = new CLIRouter(getEnrollmentHandler(), getCourseHandler(), getUserHandler());
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
