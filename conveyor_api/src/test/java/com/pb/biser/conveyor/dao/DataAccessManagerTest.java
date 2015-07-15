package com.pb.biser.conveyor.dao;

import com.pb.biser.conveyor.EmbeddedBaseManager;
import com.pb.biser.conveyor.conf.BiserConfiguration;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.testng.Assert.*;

/**
* @author Alexander Bondarenko
*         Date: 6/12/15
*         Time: 1:31 PM
*/
public class DataAccessManagerTest {
    private DataAccessManager dataAccessManager;
    private EmbeddedBaseManager manager;

    @BeforeClass
    public void setUp() throws Exception {
        dataAccessManager = new DataAccessManager();

        manager = new EmbeddedBaseManager();
        manager.getDataSource();

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES,
                "org.apache.naming");
        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/jdbc");
        ic.bind("java:comp/env/jdbc/biser_stats", manager.getDataSource());

        BiserConfiguration configuration = new BiserConfiguration();
        JdbcTemplate jdbcTemplate = configuration.jdbcTemplate();
        dataAccessManager.setTemplate(jdbcTemplate);
    }

    @BeforeMethod
    public void refreshDB() throws Exception {
        manager.dropDatabase();
        manager.fillDatabase();
    }

    @Test
    public void shouldReturnTrueIfCompanyIdDoesNotExistInTheTable() throws Exception {
        assertTrue(dataAccessManager.isActiveCompany(123456l), "Should return true if company does not exist in the table!");
    }

    @Test
    public void shouldReturnTrueIfCompanyHasAnyKindOfTestsLessTheMaxLimit() throws Exception {
        assertTrue(dataAccessManager.isActiveCompany(1l), "Should return true if company has any kind of tests less than max limit!");
    }

    @Test
    public void shouldReturnFalseIfCompanyHasTestsWhereErrorCodesOfSystemsIsTheSame() {
        assertFalse(dataAccessManager.isActiveCompany(4l), "Should return false is company has tests with the same error codes");
    }

    @Test
    public void shouldReturnFalseIfCompanyHasTestsWhereErrorCodesOfSystemsIsDifferent() throws Exception {
        assertFalse(dataAccessManager.isActiveCompany(3l), "Should return false is company has tests with different error codes");
    }

    @Test
    public void shouldReturnFalseIfCompanyHasTestsWherePacksOfSystemsIsTheSame() throws Exception {
        assertFalse(dataAccessManager.isActiveCompany(2l), "Should return false is company has tests with the same packs");
    }

    @Test
    public void shouldReturnFalseIfCompanyHasTestsWhereApiSystemResponseOkAndBISERResponseHasError() throws Exception {
        assertFalse(dataAccessManager.isActiveCompany(5l), "Should return false is company has tests with api ok and biser error");
    }

    @Test
    public void shouldReturnFalseIfCompanyHasTestsWhereApiSystemResponseHasErrorAndBISERResponseOk() throws Exception {
        assertFalse(dataAccessManager.isActiveCompany(6l), "Should return false is company has tests with api error and biser ok");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldReturnNullPointerExceptionIfIncomingObjectNotSpecified() throws Exception {
        dataAccessManager.putTestData(null);
    }

    @Test
    public void shouldSuccessfullyCreateRowInDatabase() throws Exception {
        SearchRequestAttributes attributes = DataAccessManagerFixtures.create().fullObject();

        dataAccessManager.putTestData(attributes);

        String sql = "SELECT COUNT(*) FROM conveyor.records WHERE company_id = " + attributes.getCompanyId();

        DataSource dataSource = manager.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(resultSet.getInt(1), 1);
            assertFalse(resultSet.next());
        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

    @Test
    public void shouldSuccessfullyCreateRowInDatabaseIfBISERPackIsNull() throws Exception {
        SearchRequestAttributes attributes = DataAccessManagerFixtures.create().withoutBISERPack();

        dataAccessManager.putTestData(attributes);

        String sql = "SELECT biser_pack FROM conveyor.records WHERE company_id = " + attributes.getCompanyId();

        DataSource dataSource = manager.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertNull(resultSet.getString(1));
            assertFalse(resultSet.next());
        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

    @Test
    public void shouldSuccessfullyCreateRowInDatabaseIfAPI2XPackIsNull() throws Exception {
        SearchRequestAttributes attributes = DataAccessManagerFixtures.create().withoutAPI2XPack();

        dataAccessManager.putTestData(attributes);

        String sql = "SELECT api_pack FROM conveyor.records WHERE company_id = " + attributes.getCompanyId();

        DataSource dataSource = manager.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertNull(resultSet.getString(1));
            assertFalse(resultSet.next());
        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

    @Test
    public void shouldReturnEmptySetWhileCallingGetInvalidWithOnlyActiveCompaniesAvailable() throws Exception {
        dataAccessManager.kill(2l);
        dataAccessManager.kill(3l);
        dataAccessManager.kill(4l);
        dataAccessManager.kill(5l);
        dataAccessManager.kill(6l);
        assertTrue(dataAccessManager.getInvalidTestCompanies().isEmpty());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldReturnNullPointerExceptionIfCompanyIdNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutCompanyId());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldReturnNullPointerExceptionIfPointNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutPoint());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void shouldReturnNullPointerExceptionIfBISERCodeNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutBISERCode());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void shouldReturnNullPointerExceptionIfAPI2XCodeNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutAPI2XCode());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void shouldReturnNullPointerExceptionIfSIDNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutSID());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldReturnNullPointerExceptionIfParametersNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutParameters());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldReturnNullPointerExceptionIfTypeNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutType());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldReturnNullPointerExceptionIfStatusNotSpecified() {
        dataAccessManager.putTestData(DataAccessManagerFixtures.create().withoutStatus());
    }

    @AfterClass
    public void tearDown() throws Exception {
        manager.closeDatabase();
    }
}