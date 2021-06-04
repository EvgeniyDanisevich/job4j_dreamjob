package ru.job4j.dream.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class ConnectionPool {
    private static final BasicDataSource INSTANCE = new BasicDataSource();

    public ConnectionPool() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        INSTANCE.setDriverClassName(cfg.getProperty("jdbc.driver"));
        INSTANCE.setUrl(cfg.getProperty("jdbc.url"));
        INSTANCE.setUsername(cfg.getProperty("jdbc.username"));
        INSTANCE.setPassword(cfg.getProperty("jdbc.password"));
        INSTANCE.setMinIdle(5);
        INSTANCE.setMaxIdle(10);
        INSTANCE.setMaxOpenPreparedStatements(100);
    }

    public BasicDataSource getInstance() {
        return INSTANCE;
    }
}
