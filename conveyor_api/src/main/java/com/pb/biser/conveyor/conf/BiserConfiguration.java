package com.pb.biser.conveyor.conf;

import com.pb.biser.conveyor.dao.DataAccessManager;
import com.pb.biser.conveyor.dao.DataAccessManagerI;
import com.pb.biser.conveyor.properties.BiserProperties;
import com.pb.bpln.cashier.bridge.biser.client.JsonDebtWebClient;
import com.pb.bpln.cashier.bridge.biser_new.common.WebRequestProcessorImpl;
import com.pb.bpln.cashier.bridge.debt.client.DebtClientI;
import com.pb.bpln.cashier.bridge.debt.client.DebtWebClient;
import com.pb.bpln.cashier.bridge.debt.settings.BiserConnectionSettings;
import com.pb.bpln.cashier.bridge.debt.utils.DebtExceptionHandler;
import com.pb.bpln.cashier.bridge.debt.utils.ExceptionHandlerI;
import com.pb.bpln.cashier.bridge.trigger.PresearchServiceTrigger;
import com.pb.bpln.cashier.bridge.trigger.SearchServiceTrigger;
import com.pb.bpln.cashier.core.global_access.GlobalOptionsAccessI;
import com.pb.bpln.core.exeption.BaseException;
import com.pb.debt.api.client.ConnectionSettings;
import com.pb.debt.exceptions.DebtException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by diver on 5/15/15.
 */
@Configuration
@ComponentScan({
        "com.pb.biser.conveyor.dao",
})
public class BiserConfiguration {

    @Bean
    public SearchServiceTrigger searchServiceTrigger() {
        SearchServiceTrigger searchServiceTrigger = new SearchServiceTrigger();
        searchServiceTrigger.setExceptionHandler(exceptionHandler());
        searchServiceTrigger.setWebRequestProcessor(new WebRequestProcessorImpl(globalOptionAccess(), null));
        searchServiceTrigger.setJsonClient(jsonDebtWebClient());
        searchServiceTrigger.setGlobalOptionsAccess(globalOptionAccess());
        return searchServiceTrigger;
    }

    @Bean
    public PresearchServiceTrigger presearchServiceTrigger() {
        PresearchServiceTrigger presearchServiceTrigger = new PresearchServiceTrigger();
        presearchServiceTrigger.setExceptionHandler(exceptionHandler());
        presearchServiceTrigger.setWebRequestProcessor(new WebRequestProcessorImpl(globalOptionAccess(), null));
        presearchServiceTrigger.setJsonClient(jsonDebtWebClient());
        presearchServiceTrigger.setGlobalOptionsAccess(globalOptionAccess());
        return presearchServiceTrigger;
    }

    private JsonDebtWebClient jsonDebtWebClient() {
        return new JsonDebtWebClient(connectionSettings());
    }

    @Bean
    public ExceptionHandlerI<DebtException, BaseException> exceptionHandler() {
        return new DebtExceptionHandler();
    }

    @Bean
    public DebtClientI debtClient() {
        return new DebtWebClient(connectionSettings());
    }

    @Bean
    public ConnectionSettings connectionSettings() {
        return new BiserConnectionSettings(globalOptionAccess(), Boolean.FALSE);
    }

    @Bean
    public GlobalOptionsAccessI globalOptionAccess() {
        return new BiserProperties();
    }

    @Bean
    public DataSource biserDataSource() throws NamingException {
        JndiTemplate jndi = new JndiTemplate();
        return (DataSource) jndi.lookup("java:comp/env/jdbc/biser_stats");
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws NamingException {
        return new JdbcTemplate(biserDataSource());
    }

    @Bean
    public DataAccessManagerI dataAccessManager() throws NamingException {
        DataAccessManager dataAccessManager = new DataAccessManager();
        dataAccessManager.setTemplate(jdbcTemplate());
        return dataAccessManager;
    }
}
