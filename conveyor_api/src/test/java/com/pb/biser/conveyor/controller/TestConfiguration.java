package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.conf.ApplicationConfiguration;
import com.pb.biser.conveyor.dao.DataAccessManagerI;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * @author tasman
 */
public class TestConfiguration {

    @Bean
    public DataAccessManagerI dataAccessManager() {
        DataAccessManagerI dataAccessManager = mock(DataAccessManagerI.class);
        return dataAccessManager;
    }

    @Bean
    public DataSource biserDataSource() throws NamingException {
        return (mock(DataSource.class));
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws NamingException {
        return new JdbcTemplate(biserDataSource());
    }

}
