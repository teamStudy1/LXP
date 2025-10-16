package com.lxp.config;

import java.sql.Connection;
import java.sql.SQLException;

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
}
