package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.controller.entity.CompanyStats;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import com.pb.biser.conveyor.service.StatsService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class StatisticControllerTest {

    private StatisticController controller;
    private StatsService statsService;

    @BeforeMethod
    public void setUp() throws Exception {
        controller = new StatisticController();
        statsService = mock(StatsService.class);
        controller.setStatsService(statsService);


    }

    @Test
    public void shouldReturnStatsFormCompany() throws Exception {
        CompanyStats expected = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, true);
        when(statsService.getStatsForCompany(StatsFixtures.COMPANY_ID)).thenReturn(expected);

        CompanyStats result = controller.getStatsForCompany(StatsFixtures.COMPANY_ID);

        assertEquals(result, expected);
    }

    @Test
    public void shouldReturnStatsForAllCompanies() throws Exception {
        CompanyStats activeCompany = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, true);
        CompanyStats inactiveCompany = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID_2, false);
        List<CompanyStats> expected = Arrays.asList(activeCompany, inactiveCompany);
        when(statsService.getStatsForAllCompanies()).thenReturn(expected);

        List<CompanyStats> result = controller.getStatsForCompanies(null);

        assertEquals(result, expected);
    }

    @Test
    public void shouldReturnStatsForActiveCompanies() throws Exception {
        CompanyStats company1 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, true);
        CompanyStats company2 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID_2, true);
        List<CompanyStats> expected = Arrays.asList(company1, company2);
        when(statsService.getStatsForActiveCompanies()).thenReturn(expected);

        List<CompanyStats> result = controller.getStatsForCompanies(true);

        assertEquals(result, expected);
    }

    @Test
    public void shouldReturnStatsForInactiveCompanies() throws Exception {
        CompanyStats company1 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID, false);
        CompanyStats company2 = StatsFixtures.createCompanyStats(StatsFixtures.COMPANY_ID_2, false);
        List<CompanyStats> expected = Arrays.asList(company1, company2);
        when(statsService.getStatsForInactiveCompanies()).thenReturn(expected);

        List<CompanyStats> result = controller.getStatsForCompanies(false);

        assertEquals(result, expected);
    }

    @Test
    public void shouldAddCompanyStats() throws Exception {
        SearchRequestAttributes expected = StatsFixtures.createSearchRequestAttributes();

        SearchRequestAttributes result = controller.addRequestAttributes(expected);

        assertEquals(result, expected);
        verify(statsService).storeAttributes(expected);
    }

}