package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.controller.entity.BiserResponse;
import com.pb.biser.conveyor.controller.entity.CompanyStats;
import com.pb.biser.conveyor.controller.entity.Response;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import com.pb.biser.conveyor.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tasman
 */

@RestController
public class StatisticController {


    private StatsService statsService;

    @Autowired
    public void setStatsService(StatsService statsService) {
        this.statsService = statsService;
    }

    @RequestMapping(value = "/stats/attributes", method = RequestMethod.POST)
    public
    @ResponseBody
    SearchRequestAttributes addRequestAttributes(@RequestBody SearchRequestAttributes searchRequestAttributes) {
        statsService.storeAttributes(searchRequestAttributes);
        return searchRequestAttributes;
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public
    @ResponseBody
    CompanyStats getStatsForCompany(@RequestParam("company_id") Long companyId) {
        CompanyStats response = statsService.getStatsForCompany(companyId);
        return response;
    }

    @RequestMapping(value = "/stats/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CompanyStats> getStatsForCompanies(@RequestParam(value = "showActive", required = false) Boolean active) {
        if (active == null) {
            return statsService.getStatsForAllCompanies();
        }
        return active ? statsService.getStatsForActiveCompanies() : statsService.getStatsForInactiveCompanies();
    }

    @ExceptionHandler(Exception.class)
    public Response throwJsonError(Exception ex) {
        Response response = new Response(new BiserResponse("DT_SYS", ex.getMessage()));
        return response;
    }


}
