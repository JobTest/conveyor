package com.pb.biser.conveyor.service;

import com.pb.biser.conveyor.controller.StatsFixtures;
import com.pb.biser.conveyor.controller.entity.CompanyStats;
import com.pb.biser.conveyor.dao.DataAccessManagerI;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class StatsServiceTest {

    private DataAccessManagerI dataAccessManager;
    private StatsService service;

    @BeforeMethod
    public void setUp() throws Exception {
        dataAccessManager = mock(DataAccessManagerI.class);
        service = new StatsService(dataAccessManager);
    }

    @Test
    public void shouldStoreRequestAttributes() throws Exception {
        SearchRequestAttributes attrs = StatsFixtures.createSearchRequestAttributes();
        attrs.setCompanyId(StatsFixtures.COMPANY_ID);

        service.storeAttributes(attrs);

        verify(dataAccessManager).putTestData(attrs);
    }

    @Test
    public void testGetStatsForCompany() throws Exception {
        CompanyStats expected = StatsFixtures.createCompanyStats();
        when(dataAccessManager.isActiveCompany(StatsFixtures.COMPANY_ID)).thenReturn(true);

        CompanyStats result = service.getStatsForCompany(StatsFixtures.COMPANY_ID);

        assertEquals(result, expected);
    }

    @Test
    public void testGetStatsForAllCompanies() throws Exception {
        CompanyStats stats1 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, true);
        CompanyStats stats2 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID_2, false);
        List<CompanyStats> expected = Arrays.asList(stats1, stats2);
        when(dataAccessManager.getValidTestCompanies()).thenReturn(Collections.singleton(StatsFixtures.COMPANY_ID.intValue()));
        when(dataAccessManager.getInvalidTestCompanies()).thenReturn(Collections.singleton(StatsFixtures.COMPANY_ID_2.intValue()));

        List<CompanyStats> result = service.getStatsForAllCompanies();

        assertEquals(result, expected);
    }

    @Test
    public void testGetStatsForActiveCompanies() throws Exception {
        CompanyStats stats1 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, true);
        CompanyStats stats2 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID_2, true);
        List<CompanyStats> expected = Arrays.asList(stats1, stats2);
        when(dataAccessManager.getValidTestCompanies()).thenReturn(new LinkedHashSet<>(Arrays.asList(StatsFixtures.COMPANY_ID.intValue(), StatsFixtures.COMPANY_ID_2.intValue())));

        List<CompanyStats> result = service.getStatsForActiveCompanies();

        assertEquals(result, expected);
    }

    @Test
    public void testGetStatsForInactiveCompanies() throws Exception {
        CompanyStats stats1 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, false);
        CompanyStats stats2 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID_2, false);
        List<CompanyStats> expected = Arrays.asList(stats1, stats2);
        when(dataAccessManager.getInvalidTestCompanies()).thenReturn(new LinkedHashSet<>(Arrays.asList(StatsFixtures.COMPANY_ID.intValue(), StatsFixtures.COMPANY_ID_2.intValue())));

        List<CompanyStats> result = service.getStatsForInactiveCompanies();

        assertEquals(result, expected);
    }
}