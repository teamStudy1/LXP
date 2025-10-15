package com.lxp.component;

import com.lxp.handler.EnrollmentHandler;
import com.lxp.infrastructure.dao.EnrollmentDao;
import com.lxp.service.EnrollmentService;
import com.lxp.util.CLIRouter;

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
        private static final com.lxp.api.controller.EnrollmentController INSTANCE =
                new com.lxp.api.controller.EnrollmentController(getEnrollmentService());
    }

    public static EnrollmentDao getEnrollmentDao() {
        return EnrollmentDaoHolder.INSTANCE;
    }

    public static EnrollmentService getEnrollmentService() {
        return EnrollmentServiceHolder.INSTANCE;
    }

    public static com.lxp.api.controller.EnrollmentController getEnrollmentController() {
        return EnrollmentControllerHolder.INSTANCE;
    }

    // handleController component
    private static class HandleControllerHolder {
        private static final EnrollmentHandler INSTANCE =
                new EnrollmentHandler(getEnrollmentController());
    }

    public static EnrollmentHandler getHandleController() {
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
