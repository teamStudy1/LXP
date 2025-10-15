package com.lxp.config;

import com.lxp.api.controller.UserController;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;
import com.lxp.infrastructure.dao.EnrollmentDao;
import com.lxp.infrastructure.dao.UserDao;
import com.lxp.service.EnrollmentService;
import com.lxp.service.UserService;
import com.lxp.util.CLIRouter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
    private static final HikariDataSource dataSource;

    static {
        try {
            Properties prop = new Properties();
            prop.load(JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties"));
            HikariConfig config = new HikariConfig(prop);
            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static void printConnectionPoolStatus() {
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("hikaricp 커넥션 풀 상태");
        System.out.println("총 커넥션 개수" + poolMXBean.getTotalConnections());
        System.out.println("활성 커넥션 개수" + poolMXBean.getActiveConnections());
        System.out.println("유후 커넥션 개수" + poolMXBean.getIdleConnections());
        System.out.println(
                "대기중인 커넥션 요청 수" + poolMXBean.getThreadsAwaitingConnection()); // 풀에서 연결을 기다리는 스레드 수를 가져옴
    }

    public static class ApplicationContext {

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

        // router component
        private static class RouterHolder {
            private static final CLIRouter INSTANCE = new CLIRouter(getHandleController(), getUserHandler());
        }

        public static CLIRouter getRouter() {
            return RouterHolder.INSTANCE;
        }
    }
}
