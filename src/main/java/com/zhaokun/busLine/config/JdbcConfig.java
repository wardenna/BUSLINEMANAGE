package com.zhaokun.busLine.config;

import com.zhaokun.busLine.data.db.jdbc.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("zhaokun");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSourced) {
        return new DataSourceTransactionManager(dataSourced);
    }

    @Bean
    public JdbcBusLineRepository jdbcBusLineRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcBusLineRepository(jdbcTemplate);
    }

    @Bean
    public JdbcBusStationRepository jdbcBusStationRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcBusStationRepository(jdbcTemplate);
    }

    @Bean
    public JdbcUserRepository jdbcUserRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcUserRepository(jdbcTemplate);
    }

    @Bean
    public JdbcCommentRepository jdbcCommentRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcCommentRepository(jdbcTemplate);
    }

    @Bean
    public JdbcNewsRepository jdbcNewsRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcNewsRepository(jdbcTemplate);
    }

    @Bean
    public JdbcReplyRepository jdbcReplyRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcReplyRepository(jdbcTemplate);
    }
}
