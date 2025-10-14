package com.lxp.component;

import com.lxp.api.controller.EnrollmentController;
import com.lxp.infrastructure.dao.EnrollmentDao;
import com.lxp.service.EnrollmentService;
import com.lxp.util.CLIRouter;
import com.lxp.util.HandleController;

public class ApplicationContext {

    private ApplicationContext() {}

    // enrollment component
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

    public static EnrollmentDao getEnrollmentDao() {
        return EnrollmentDaoHolder.INSTANCE;
    }

    public static EnrollmentService getEnrollmentService() {
        return EnrollmentServiceHolder.INSTANCE;
    }

    public static EnrollmentController getEnrollmentController() {
        return EnrollmentControllerHolder.INSTANCE;
    }

    // handleController component
    private static class HandleControllerHolder {
        private static final HandleController INSTANCE =
                new HandleController(getEnrollmentController());
    }

    public static HandleController getHandleController() {
        return HandleControllerHolder.INSTANCE;
    }

    // router component
    private static class RouterHolder {
        private static final CLIRouter INSTANCE = new CLIRouter(getHandleController());
    }

    public static CLIRouter getRouter() {
        return RouterHolder.INSTANCE;
    }
}
