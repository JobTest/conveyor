package com.pb.biser.conveyor.service;

import com.pb.biser.conveyor.controller.entity.BiserRequest;
import com.pb.bpln.cashier.bridge.trigger.PresearchServiceTrigger;
import com.pb.bpln.cashier.bridge.trigger.SearchServiceTrigger;
import com.pb.bpln.cashier.core.debt.search.SearchRequisites;
import com.pb.bpln.core.exeption.BaseException;
import com.pb.bpln.core.param.SearchParamContainer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by diver on 5/15/15.
 */
@Service
public class BiserService {
    private static final String PRESEARCH_ID = "presearchId";
    private static final String PRESEARCH = "PRESEARCH";

    @Autowired
    private SearchServiceTrigger searchServiceTrigger;
    @Autowired
    private PresearchServiceTrigger presearchServiceTrigger;

    public void setSearchServiceTrigger(SearchServiceTrigger searchServiceTrigger) {
        this.searchServiceTrigger = searchServiceTrigger;
    }

    public void setPresearchServiceTrigger(PresearchServiceTrigger presearchServiceTrigger) {
        this.presearchServiceTrigger = presearchServiceTrigger;
    }

    public Object call(BiserRequest request) throws BaseException {

        Long point = request.getPoint();
        Long companyId = request.getCompanyId();
        String type = request.getType();
        Map<String, String> parameters = request.getParameters();

        SearchParamContainer paramContainer = new SearchParamContainer();

        String presearchId = parameters.get(PRESEARCH_ID);

        if (StringUtils.isNotBlank(presearchId)) {
            paramContainer.addSearchParam(SearchParamContainer.BILL_MULTIPLE_CHOICE_PROPERTY, presearchId);
        } else {
            paramContainer.setSearchParamMap(parameters);
        }

        SearchRequisites searchRequisites = new SearchRequisites(companyId, paramContainer);

        if (PRESEARCH.equals(type)) {
            return presearchServiceTrigger.call(searchRequisites, point);
        }
        return searchServiceTrigger.call(searchRequisites, point);
    }
}
