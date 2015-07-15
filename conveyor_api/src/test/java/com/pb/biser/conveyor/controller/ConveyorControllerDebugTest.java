package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.conf.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by diver on 5/19/15.
 */
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Test(groups = "integration")
public class ConveyorControllerDebugTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeTest
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_1() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node1\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_OK"))).
                andExpect(jsonPath("$.biserResponse.body", is("DebtPack"))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: API2X Code = DT_SYS")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_2() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node2\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_OK"))).
                andExpect(jsonPath("$.biserResponse.body", is("DebtPack"))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: Success equals result")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_3() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node3\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_OK"))).
                andExpect(jsonPath("$.biserResponse.body", is("DebtPack1"))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: Different pack objects")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_4() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node4\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_0000X"))).
                andExpect(jsonPath("$.biserResponse.body", is(nullValue()))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: API2X = DT_OK, BISER = ERROR Code")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_5() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node5\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_OK"))).
                andExpect(jsonPath("$.biserResponse.body", is("DebtPack"))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: API2X = ERROR Code, BISER = DT_OK")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_6() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node6\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_0000X"))).
                andExpect(jsonPath("$.biserResponse.body", is("DebtPack"))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: API2X and BISER = Same ERROR Code")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_7() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node7\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_0000X2"))).
                andExpect(jsonPath("$.biserResponse.body", is(nullValue()))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: API2X and BISER != ERROR Code")));
    }

    @Test
    public void shouldReturnResponseWithMockDataOfTest__NODE_8() throws Exception {
        mockMvc.perform(post("/call/search").
                header("debug", "NtcbnhjdfybtRjydtqthfDL',fuHt;bvt").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"biserRequest\":{\"parameters\":{\"node\":\"node8\"}}}")).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.biserResponse.code", is("DT_OK"))).
                andExpect(jsonPath("$.biserResponse.body", is(nullValue()))).
                andExpect(jsonPath("$.biserResponse.message", is("TC on: Debt pack from API2X or BISER is Empty")));
    }
}
