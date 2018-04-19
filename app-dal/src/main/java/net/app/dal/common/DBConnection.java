package net.app.dal.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Properties;

@Slf4j
public class DBConnection {

    private static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String MAX_POOL_SIZE = "MAX_POOL_SIZE";
    private static final String MIN_POOL_IDLE = "MIN_POOL_IDLE";

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public DBConnection(String username, String password, String url, boolean isReadOnly) {
        Properties mySqlProperties = new Properties();

        mySqlProperties.setProperty("dataSource.cachePrepStmts", "true");
        mySqlProperties.setProperty("dataSource.prepStmtCacheSize", "100");
        mySqlProperties.setProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        mySqlProperties.setProperty("dataSource.useServerPrepStmts", "false");
        mySqlProperties.setProperty("dataSource.useLocalSessionState", "true");
        mySqlProperties.setProperty("dataSource.useLocalTransactionState", "true");
        mySqlProperties.setProperty("dataSource.cacheResultSetMetadata", "true");
        mySqlProperties.setProperty("dataSource.cacheServerConfiguration","true");
        mySqlProperties.setProperty("dataSource.elideSetAutoCommits", "true");
        mySqlProperties.setProperty("dataSource.maintainTimeStats", "false");

        dataSource = new DataSource();
        dataSource.setDriverClassName(JDBC_DRIVER_NAME);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(Integer.parseInt(System.getenv(MAX_POOL_SIZE)));
        dataSource.setMinIdle(Integer.parseInt(System.getenv(MIN_POOL_IDLE)));
        dataSource.setInitialSize(Integer.parseInt(System.getenv(MAX_POOL_SIZE)));
        dataSource.setMaxAge(60000);
        dataSource.setDefaultReadOnly(isReadOnly);

        // save in case we do xray
        //dataSource.setJdbcInterceptors("com.amazonaws.xray.sql.mysql.TracingInterceptor;");

        dataSource.setDbProperties(mySqlProperties);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
