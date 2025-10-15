package com.lxp.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionManager {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private TransactionManager() {}

    public static void beginTransaction() throws SQLException {
        Connection conn = JDBCConnection.getConnection();
        conn.setAutoCommit(false);
        connectionHolder.set(conn);
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn == null) {
            conn = JDBCConnection.getConnection();
            connectionHolder.set(conn);
        }
        return conn;
    }

    public static void commit() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            conn.commit();
            conn.close();
        }
    }

    public static void rollback() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            conn.rollback();
        }
    }

    public static void close() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.close();
            } finally {
                connectionHolder.remove();
            }
        }
    }

    public static class DatabaseInitializer {
        private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

        public static void initialize() {
            try {
                log.info("=== Database Initialization Started ===");

                executeSqlFile("/init/schema.sql");

                executeSqlFile("/init/data.sql");

                log.info("=== Database Initialization Completed ===");

            } catch (Exception e) {
                log.error("Database initialization failed: {}", e.getMessage());
                throw new RuntimeException("Failed to initialize database", e);
            }
        }

        private static void executeSqlFile(String filePath) throws Exception {
            String sql = readSqlFile(filePath);

            try (Connection conn = JDBCConnection.getConnection();
                    Statement stmt = conn.createStatement()) {

                String[] statements = sql.split(";");
                for (String statement : statements) {
                    String trimmed = statement.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                        stmt.execute(trimmed);
                    }
                }
            }
        }

        private static String readSqlFile(String filePath) throws Exception {
            InputStream is = DatabaseInitializer.class.getResourceAsStream(filePath);
            if (is == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader
                        .lines()
                        .filter(line -> !line.trim().startsWith("--")) // 주석 제거
                        .collect(Collectors.joining("\n"));
            }
        }
    }
}
