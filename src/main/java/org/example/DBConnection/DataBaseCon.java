package org.example.DBConnection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataBaseCon {

    private static HikariDataSource data;

    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/bluescope");
        config.setUsername("root");
        config.setPassword("siva@1431");


        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(20000);
        config.setIdleTimeout(30000);
        data = new HikariDataSource(config);
    }
    public static HikariDataSource getDataSource() {
        return data;
    }
}
