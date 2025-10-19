package com.lxp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {
    private static final HikariDataSource dataSource;

    static {
        try {
            Properties prop = new Properties();
            prop.load(DataSourceFactory.class.getClassLoader().getResourceAsStream("config.properties"));
            HikariConfig config = new HikariConfig(prop);
            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}