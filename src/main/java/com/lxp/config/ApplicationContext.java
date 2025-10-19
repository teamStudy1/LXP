package com.lxp.config;

import com.lxp.api.controller.UserController;
import com.lxp.category.persistance.JdbcCategoryRepository;
import com.lxp.category.persistance.dao.CategoryDao;
import com.lxp.category.service.CategoryService;
import com.lxp.category.web.CategoryController;
import com.lxp.course.persistence.JdbcCourseRepository;
import com.lxp.course.persistence.dao.CourseDao;
import com.lxp.course.persistence.dao.LectureDao;
import com.lxp.course.persistence.dao.SectionDao;
import com.lxp.course.persistence.dao.TagDao;
import com.lxp.course.service.CourseService;
import com.lxp.course.web.CourseController;
import com.lxp.handler.CategoryHandler;
import com.lxp.handler.CourseHandler;
import com.lxp.handler.UserHandler;
import com.lxp.infrastructure.dao.UserDao;
import com.lxp.service.UserService;
import com.lxp.util.CLIRouter;
import javax.sql.DataSource;

public class ApplicationContext {
    private ApplicationContext() {}

    public static class JdbcCategoryRepositoryHolder {
        private static final JdbcCategoryRepository INSTANCE =
                new JdbcCategoryRepository(getCategoryDao());
    }

    public static JdbcCategoryRepository getJdbcCategoryRepository() {
        return JdbcCategoryRepositoryHolder.INSTANCE;
    }

    private static class CategoryDaoHolder {
        private static final CategoryDao INSTANCE = new CategoryDao(getDataSource());
    }

    private static class CategoryServiceHolder {
        private static final CategoryService INSTANCE =
                new CategoryService(getJdbcCategoryRepository());
    }

    private static class CategoryControllerHolder {
        private static final CategoryController INSTANCE = new CategoryController(getCategoryService());
    }

    private static class CategoryHandlerHolder {
        private static final CategoryHandler INSTANCE =
                new CategoryHandler(getCategoryController(), getUserController());
    }

    public static CategoryDao getCategoryDao() {
        return CategoryDaoHolder.INSTANCE;
    }

    public static CategoryService getCategoryService() {
        return CategoryServiceHolder.INSTANCE;
    }

    public static CategoryController getCategoryController() {
        return CategoryControllerHolder.INSTANCE;
    }

    public static CategoryHandler getCategoryHandler() {
        return CategoryHandlerHolder.INSTANCE;
    }

    // user component
    private static class UserDaoHolder {
        private static final UserDao INSTANCE = new UserDao(getDataSource());
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

    private static class UserHandlerHolder {
        private static final UserHandler INSTANCE = new UserHandler(getUserController());
    }

    public static UserHandler getUserHandler() {
        return UserHandlerHolder.INSTANCE;
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

    // tag
    public static class TagDaoHolder {
        private static final TagDao INSTANCE = new TagDao(getDataSource());
    }

    public static TagDao getTagDao() {
        return TagDaoHolder.INSTANCE;
    }

    // course component
    public static class JdbcCourseRepositoryHolder {
        private static final JdbcCourseRepository INSTANCE =
                new JdbcCourseRepository(getCourseDao(), getSectionDao(), getLectureDao(), getTagDao());
    }

    public static JdbcCourseRepository getJdbcCourseRepository() {
        return JdbcCourseRepositoryHolder.INSTANCE;
    }

    private static class CourseDaoHolder {
        private static final CourseDao INSTANCE = new CourseDao(getDataSource());
    }

    private static class CourseServiceHolder {
        private static final CourseService INSTANCE =
                new CourseService(getJdbcCourseRepository(), getJdbcCategoryRepository());
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
                new CLIRouter(getCourseHandler(), getUserHandler(), getCategoryHandler());
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
