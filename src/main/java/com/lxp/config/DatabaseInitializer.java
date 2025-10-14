package com.lxp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
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
            return reader.lines()
                    .filter(line -> !line.trim().startsWith("--")) // 주석 제거
                    .collect(Collectors.joining("\n"));
        }
    }
}

