package com.pb.biser.conveyor.service;

import com.pb.biser.conveyor.NullSafe;
import com.pb.biser.conveyor.controller.entity.CompanyStats;
import com.pb.biser.conveyor.dao.DataAccessManagerI;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author tasman
 */
@Service
public class StatsService {

    private DataAccessManagerI dataAccessManager;

    @Autowired
    public StatsService(DataAccessManagerI dataAccessManager) {
        this.dataAccessManager = dataAccessManager;
    }

    public void storeAttributes(SearchRequestAttributes attributes) {
        NullSafe.argumentIsNotNull(attributes.getCompanyId(), "companyId");
        dataAccessManager.putTestData(attributes);
    }

    public CompanyStats getStatsForCompany(Long companyId) {
        NullSafe.argumentIsNotNull(companyId, "companyId");
        boolean active = dataAccessManager.isActiveCompany(companyId);
        CompanyStats result = new CompanyStats();
        result.setActive(active);
        result.setId(companyId);
        return result;
    }

    public List<CompanyStats> getStatsForAllCompanies() {
        List<CompanyStats> result = getStatsForActiveCompanies();
        List<CompanyStats> statsForInactiveCompanies = getStatsForInactiveCompanies();
        result.addAll(statsForInactiveCompanies);
        return result;
    }


    public List<CompanyStats> getStatsForActiveCompanies() {
        return createStats(dataAccessManager.getValidTestCompanies(), true);
    }

    public List<CompanyStats> getStatsForInactiveCompanies() {
        return createStats(dataAccessManager.getInvalidTestCompanies(), false);
    }

    private List<CompanyStats> createStats(Set<Integer> companyIds, boolean active) {
        List<CompanyStats> result = new LinkedList<>();

        companyIds.forEach(id -> result.add(createCompanyStats(id, active)));

        return result;
    }

    private CompanyStats createCompanyStats(Integer id, boolean active) {
        CompanyStats result = new CompanyStats();
        result.setId(Long.valueOf(id));
        result.setActive(active);
        return result;
    }

}
