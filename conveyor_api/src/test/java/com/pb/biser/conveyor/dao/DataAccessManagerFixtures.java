package com.pb.biser.conveyor.dao;

import com.pb.biser.conveyor.controller.entity.Type;
import com.pb.biser.conveyor.dao.entity.Diff;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by diver on 6/16/15.
 */
public final class DataAccessManagerFixtures {

    private SearchRequestAttributes searchRequestAttributes;
    private Diff diff;

    private DataAccessManagerFixtures() {
        searchRequestAttributes = new SearchRequestAttributes();
        searchRequestAttributes.setCompanyId(213123L);
        searchRequestAttributes.setPoint(13L);
        searchRequestAttributes.setSession("asd989a87d9");
        searchRequestAttributes.setStatus(3);
        searchRequestAttributes.setType(Type.SEARCH);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("ACCOUNT", "000120121");
        searchRequestAttributes.setParameters(parameters);
        diff = new Diff("Pack", "Pack");
        diff.setApi2xCode("DT_OK");
        diff.setBiserCode("DT_OK");

        searchRequestAttributes.setDiff(diff);
    }

    public SearchRequestAttributes withoutBISERPack() {
        diff.setBiserPack(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutBISERCode() {
        diff.setBiserCode(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutAPI2XPack() {
        diff.setApi2xPack(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutAPI2XCode() {
        diff.setApi2xCode(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutParameters() {
        searchRequestAttributes.setParameters(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutCompanyId() {
        searchRequestAttributes.setCompanyId(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutPoint() {
        searchRequestAttributes.setPoint(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutStatus() {
        searchRequestAttributes.setStatus(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutType() {
        searchRequestAttributes.setType(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes withoutSID() {
        searchRequestAttributes.setSession(null);
        return searchRequestAttributes;
    }

    public SearchRequestAttributes fullObject() {
        return searchRequestAttributes;
    }

    public static DataAccessManagerFixtures create() {
        return new DataAccessManagerFixtures();
    }
}
