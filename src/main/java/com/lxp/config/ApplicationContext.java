package com.lxp.config;

import com.lxp.api.controller.CategoryController;
import com.lxp.api.controller.CourseController;
import com.lxp.api.controller.EnrollmentController;
import com.lxp.api.controller.UserController;
import com.lxp.handler.CategoryHandler;
import com.lxp.handler.CourseHandler;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;
import com.lxp.infrastructure.dao.*;
import com.lxp.service.*;
import com.lxp.util.CLIRouter;

import javax.sql.DataSource;

public class ApplicationContext {
    private ApplicationContext() {}

    // ================= DataSource Component =================
    private static class DataSourceHolder {
        private static final DataSource INSTANCE = DataSourceFactory.getDataSource();
    }
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    // ================= DAO Components =================
    private static class CourseDaoHolder {
        private static final CourseDao INSTANCE = new JdbcCourseDao(getDataSource());
    }
    private static class CourseDetailDaoHolder {
        private static final CourseDetailDao INSTANCE = new JdbcCourseDetailDao();
    }
    private static class TagDaoHolder {
        private static final TagDao INSTANCE = new JdbcTagDao();
    }
    private static class CourseTagDaoHolder {
        private static final CourseTagDao INSTANCE = new JdbcCourseTagDao();
    }
    private static class UserDaoHolder {
        private static final UserDao INSTANCE = new UserDao(getDataSource());
    }
    private static class EnrollmentDaoHolder {
        private static final EnrollmentDao INSTANCE = new EnrollmentDao();
    }
    private static class CategoryDaoHolder {
        private static final CategoryDao INSTANCE = new CategoryDao();
    }
    public static CourseDao getCourseDao() { return CourseDaoHolder.INSTANCE; }
    public static CourseDetailDao getCourseDetailDao() { return CourseDetailDaoHolder.INSTANCE; }
    public static TagDao getTagDao() { return TagDaoHolder.INSTANCE; }
    public static CourseTagDao getCourseTagDao() { return CourseTagDaoHolder.INSTANCE; }
    public static UserDao getUserDao() { return UserDaoHolder.INSTANCE; }
    public static EnrollmentDao getEnrollmentDao() { return EnrollmentDaoHolder.INSTANCE; }
    public static CategoryDao getCategoryDao() { return CategoryDaoHolder.INSTANCE; }


    // ================= Service Components =================
    private static class CourseServiceHolder {
        private static final CourseService INSTANCE = new CourseService(getCourseDao(), getCourseDetailDao(), getTagDao(), getCourseTagDao());
    }
    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService(getUserDao());
    }
    private static class EnrollmentServiceHolder {
        private static final EnrollmentService INSTANCE = new EnrollmentService(getEnrollmentDao());
    }
    private static class CategoryServiceHolder {
        private static final CategoryService INSTANCE = new CategoryService(getCategoryDao());
    }
    public static CourseService getCourseService() { return CourseServiceHolder.INSTANCE; }
    public static UserService getUserService() { return UserServiceHolder.INSTANCE; }
    public static EnrollmentService getEnrollmentService() { return EnrollmentServiceHolder.INSTANCE; }
    public static CategoryService getCategoryService() { return CategoryServiceHolder.INSTANCE; }


    // ================= Controller Components =================
    private static class CourseControllerHolder {
        private static final CourseController INSTANCE = new CourseController(getCourseService());
    }
    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController(getUserService());
    }
    private static class EnrollmentControllerHolder {
        private static final EnrollmentController INSTANCE = new EnrollmentController(getEnrollmentService());
    }
    private static class CategoryControllerHolder {
        private static final CategoryController INSTANCE = new CategoryController(getCategoryService());
    }
    public static CourseController getCourseController() { return CourseControllerHolder.INSTANCE; }
    public static UserController getUserController() { return UserControllerHolder.INSTANCE; }
    public static EnrollmentController getEnrollmentController() { return EnrollmentControllerHolder.INSTANCE; }
    public static CategoryController getCategoryController() { return CategoryControllerHolder.INSTANCE; }


    // ================= Handler Components =================
    private static class CourseHandlerHolder {
        private static final CourseHandler INSTANCE = new CourseHandler(getCourseController());
    }
    private static class UserHandlerHolder {
        private static final UserHandler INSTANCE = new UserHandler(getUserController());
    }
    private static class EnrollmentHandlerHolder {
        private static final EnrollmentHandler INSTANCE = new EnrollmentHandler(getEnrollmentController());
    }
    private static class CategoryHandlerHolder {
        // [오류 해결] CategoryHandler 생성 시, UserController도 함께 전달합니다.
        private static final CategoryHandler INSTANCE = new CategoryHandler(getCategoryController(), getUserController());
    }
    public static CourseHandler getCourseHandler() { return CourseHandlerHolder.INSTANCE; }
    public static UserHandler getUserHandler() { return UserHandlerHolder.INSTANCE; }
    public static EnrollmentHandler getEnrollmentHandler() { return EnrollmentHandlerHolder.INSTANCE; }
    public static CategoryHandler getCategoryHandler() { return CategoryHandlerHolder.INSTANCE; }


    // ================= Router Component =================
    private static class RouterHolder {
        private static final CLIRouter INSTANCE = new CLIRouter(getCourseHandler(), getEnrollmentHandler(), getUserHandler(), getCategoryHandler());
    }
    public static CLIRouter getRouter() {
        return RouterHolder.INSTANCE;
    }
}