package com.lxp.config;

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
}
