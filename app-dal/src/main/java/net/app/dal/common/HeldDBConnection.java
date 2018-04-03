package net.app.dal.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Singleton pattern approaching for creating and holding DB connections.
 */
@Slf4j
public class HeldDBConnection {
    private static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CP_RO_NAME = "rdn-ro-pool";
    private static final String CP_RW_NAME = "rdn-rw-pool";

    private static final String MAX_POOL_SIZE = "MAX_POOL_SIZE";
    private static final String MIN_POOL_IDLE = "MIN_POOL_IDLE";

    private static final String RO_USERNAME = "RO_USERNAME";
    private static final String RW_USERNAME = "RW_USERNAME";
    private static final String RO_PASSWORD = "RO_PASSWORD";
    private static final String RW_PASSWORD = "RW_PASSWORD";
    private static final String RO_URL = "RO_URL";
    private static final String RW_URL = "RW_URL";

    private JdbcTemplate jdbcTemplate;
    private HikariDataSource dataSource;

    private static HeldDBConnection readInstance;
    private static HeldDBConnection writeInstance;

    private HeldDBConnection(String poolName, String username, String password, String url) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(JDBC_DRIVER_NAME);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(Integer.parseInt(System.getenv(MAX_POOL_SIZE)));
        config.setMinimumIdle(Integer.parseInt(System.getenv(MIN_POOL_IDLE)));
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);

        dataSource = new HikariDataSource(config);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public static JdbcTemplate getReadInstance() {
        if(readInstance == null) {
            String username = System.getenv(RO_USERNAME);
            String password = System.getenv(RO_PASSWORD);
            String url = System.getenv(RO_URL);
            try {
                readInstance = new HeldDBConnection(CP_RO_NAME, username, password, url);
            } catch (Exception e) {
                log.error("Error creating read connection: ", e.getMessage());
                throw e;
            }
        }
        return readInstance.getJdbcTemplate();
    }

    public static JdbcTemplate getWriteInstance() {
        if(writeInstance == null) {
            String username = System.getenv(RW_USERNAME);
            String password = System.getenv(RW_PASSWORD);
            String url = System.getenv(RW_URL);
            writeInstance = new HeldDBConnection(CP_RW_NAME, username, password, url);
        }
        return writeInstance.getJdbcTemplate();
    }
}
