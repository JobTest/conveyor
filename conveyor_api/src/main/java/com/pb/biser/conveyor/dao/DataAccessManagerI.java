package com.pb.biser.conveyor.dao;

import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;

import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Bondarenko
 *         Date: 6/11/15
 *         Time: 2:01 PM
 */
public interface DataAccessManagerI {

    Set<Integer> getValidTestCompanies();

    Set<Integer> getInvalidTestCompanies();

    void putTestData(SearchRequestAttributes data);

    boolean isActiveCompany(Long companyId);

    Integer getLimit();

    void kill(Long companyId);

}
