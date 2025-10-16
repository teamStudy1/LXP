package com.lxp.config;

import com.lxp.api.controller.EnrollmentController;
import com.lxp.api.controller.UserController;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;
import com.lxp.infrastructure.dao.EnrollmentDao;
import com.lxp.infrastructure.dao.UserDao;
import com.lxp.service.EnrollmentService;
import com.lxp.service.UserService;
import com.lxp.util.CLIRouter;

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

    private static class RouterHolder {
        private static final CLIRouter INSTANCE = new CLIRouter(getEnrollmentHandler(), getUserHandler());
    }

    public static CLIRouter getRouter() {
        return RouterHolder.INSTANCE;
    }
}
