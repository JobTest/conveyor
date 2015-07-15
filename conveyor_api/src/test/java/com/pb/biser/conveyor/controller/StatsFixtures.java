package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.controller.entity.CompanyStats;
import com.pb.biser.conveyor.controller.entity.Type;
import com.pb.biser.conveyor.dao.entity.Diff;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;

/**
 * @author tasman
 */
public final class StatsFixtures {

    public static final Long COMPANY_ID = 123456L;
    public static final Long COMPANY_ID_2 = 123457L;

    private StatsFixtures() {
    }

    public static SearchRequestAttributes createSearchRequestAttributes() {
        SearchRequestAttributes requestAttributes = new SearchRequestAttributes();
        requestAttributes.setPoint(11L);
        requestAttributes.setSession("sid123456");
        requestAttributes.setStatus(1);
        requestAttributes.setType(Type.PRESEARCH);
        Diff diff = new Diff("biser_pack", "api_pack");
        diff.setApi2xCode("API_CODE");
        diff.setBiserCode("BISER_CODE");
        return requestAttributes;
    }

    public static CompanyStats createCompanyStats(Long companyId, boolean active) {
        CompanyStats result = new CompanyStats();
        result.setId(companyId);
        result.setActive(active);
        return result;
    }

    public static CompanyStats createCompanyStats() {
        return createCompanyStats(COMPANY_ID, true);
    }
}
