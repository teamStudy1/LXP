package com.lxp.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCConnection {
    private static final Logger log = LoggerFactory.getLogger(JDBCConnection.class);
    private static final HikariDataSource dataSource = DataSourceFactory.getDataSource();

    public static Connection getConnection() throws SQLException {
        log.debug("Requesting connection from pool...");
        Connection connection = dataSource.getConnection();
        log.debug("Connection [{}] obtained from pool.", connection);
        return connection;
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("HikariCP DataSource has been closed.");
        }
    }

    public static void printConnectionPoolStatus() {
        if (dataSource == null || dataSource.isClosed()) {
            System.out.println("HikariCP connection pool is not active.");
            return;
        }
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        System.out.println("\n--- HikariCP Connection Pool Status ---");
        System.out.println("Total Connections: " + poolMXBean.getTotalConnections());
        System.out.println("Active Connections: " + poolMXBean.getActiveConnections());
        System.out.println("Idle Connections: " + poolMXBean.getIdleConnections());
        System.out.println("Threads Awaiting Connection: " + poolMXBean.getThreadsAwaitingConnection());
        System.out.println("-------------------------------------\n");
    }
}