package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.conf.ApplicationConfiguration;
import com.pb.biser.conveyor.conf.BiserConfiguration;
import com.pb.biser.conveyor.dao.DataAccessManagerI;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author tasman
 */
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfiguration.class, BiserConfiguration.class, TestConfiguration.class})
@Test(groups = "middle")
public class StatisticControllerITest extends AbstractTestNGSpringContextTests {
    public static final String ERROR_MESSAGE = "data access error";
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private DataAccessManagerI dataAccessManager;
    private MockMvc mockMvc;

    @AfterMethod
    public void tearDown() throws Exception {
        Mockito.reset(dataAccessManager);
    }

    @Test
    public void shouldReturnStatsForSingleCompany() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(dataAccessManager.isActiveCompany(StatsFixtures.COMPANY_ID)).thenReturn(Boolean.TRUE);

        mockMvc.perform(get("/stats")
                .accept(MediaType.APPLICATION_JSON)
                .param("company_id", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(123456)));
    }

    @Test
    public void shouldReturnStatsForAllCompanies() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(dataAccessManager.getInvalidTestCompanies()).thenReturn(Collections.singleton(StatsFixtures.COMPANY_ID.intValue()));
        when(dataAccessManager.getValidTestCompanies()).thenReturn(Collections.singleton(StatsFixtures.COMPANY_ID_2.intValue()));

        mockMvc.perform(get("/stats/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id", is(StatsFixtures.COMPANY_ID_2.intValue())))
                .andExpect(jsonPath("$[0].active", is(true)))
                .andExpect(jsonPath("$[1].id", is(StatsFixtures.COMPANY_ID.intValue())))
                .andExpect(jsonPath("$[1].active", is(false)));
    }

    @Test
    public void shouldAddSearchRequestAttributes() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(post("/stats/attributes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "        \"company_id\": \"123456\",\n" +
                        "        \"session\": \"abcdefg123237782378423\",\n" +
                        "        \"point\": 11,\n" +
                        "        \"type\": \"SEARCH\",\n" +
                        "        \"status\": 1,\n" +
                        "        \"diff\": {\n" +
                        "            \"biserPack\": \"biser_pack\",\n" +
                        "            \"api2xPack\": \"api2x_pack\",\n" +
                        "            \"biserCode\": \"BS_01_01\",\n" +
                        "            \"api2xCode\": \"BPLN_01_02\"\n" +
                        "        },\n" +
                        "        \"parameters\" : {\n" +
                        "            \"param1\" : \"value1\",\n" +
                        "            \"param2\" : \"value2\"\n" +
                        "        }\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.company_id", is(StatsFixtures.COMPANY_ID.intValue())));
    }

    @Test
    public void shouldReturnStatsForActiveCompanies() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(dataAccessManager.getValidTestCompanies()).thenReturn(Collections.singleton(StatsFixtures.COMPANY_ID_2.intValue()));

        mockMvc.perform(get("/stats/all")
                .param("showActive", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id", is(StatsFixtures.COMPANY_ID_2.intValue())))
                .andExpect(jsonPath("$[0].active", is(true)));
    }

    @Test
    public void shouldReturnStatsForInactiveCompanies() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(dataAccessManager.getInvalidTestCompanies()).thenReturn(Collections.singleton(StatsFixtures.COMPANY_ID.intValue()));

        mockMvc.perform(get("/stats/all")
                .param("showActive", "false")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id", is(StatsFixtures.COMPANY_ID.intValue())))
                .andExpect(jsonPath("$[0].active", is(false)));
    }

    @Test
    public void shouldHandleExceptionCorrectly() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(dataAccessManager.getInvalidTestCompanies()).thenThrow(new RuntimeException(ERROR_MESSAGE));

        mockMvc.perform(get("/stats/all")
                .param("showActive", "false")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.biserResponse.code", is("DT_SYS")))
                .andExpect(jsonPath("$.biserResponse.message", is(ERROR_MESSAGE)));
    }
}
