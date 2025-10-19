package com.lxp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final Logger log = LoggerFactory.getLogger(TransactionManager.class);
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private TransactionManager() {}

    public static Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn == null || conn.isClosed()) {
            conn = JDBCConnection.getConnection();
            connectionHolder.set(conn);
        }
        return conn;
    }

    public static void beginTransaction() throws SQLException {
        Connection conn = JDBCConnection.getConnection();
        conn.setAutoCommit(false);
        connectionHolder.set(conn);
        log.debug("Transaction started for connection [{}].", conn);
    }

    public static void commit() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null && !conn.isClosed()) {
            try {
                conn.commit();
                log.debug("Transaction for connection [{}] committed.", conn);
            } finally {
                closeConnection();
            }
        }
    }

    public static void rollback() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.rollback();
                    log.debug("Transaction for connection [{}] rolled back.", conn);
                }
            } catch (SQLException e) {
                log.error("Failed to roll back transaction.", e);
            } finally {
                try {
                    closeConnection();
                } catch (SQLException e) {
                    log.error("Failed to close connection after rollback.", e);
                }
            }
        }
    }

    private static void closeConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null && !conn.isClosed()) {
            try {
                conn.close();
            } finally {
                connectionHolder.remove();
            }
        }
    }
}