package com.pb.biser.conveyor.stress;

import com.pb.biser.conveyor.BiserConveyorClient;
import com.pb.biser.conveyor.entity.BCRequest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by diver on 5/18/15.
 */
public class BiserConveyorClientStressAcceptTest {

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_1_if_api2x_code_eq_DT_SYS() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node1");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_SYS").
                        addCompany(123456).
                        addParameters(data).
                        addPoint("1").
                        addSession("sesion").
                        build()
        );
    }

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_2_if_both_codes_eq_DT_OK_and_both_packs_the_same() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node2");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_OK").
                        addCompany(123456).
                        addObject("DebtPack").
                        addParameters(data).
                        addPoint("1").
                        addSession("session").
                        build()
        );
    }

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_3_if_both_codes_eq_DT_OK_and_packs_not_same() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node3");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_OK").
                        addCompany(123456).
                        addObject("DebtPackX").
                        addParameters(data).
                        addPoint("1").
                        addSession("session").
                        build()
        );
    }

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_4_if_api2x_code_eq_DT_OK_and_biser_code_eq_DT_0000X() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node4");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_OK").
                        addCompany(123456).
                        addObject("DebtPack").
                        addParameters(data).
                        addPoint("1").
                        addSession("session").
                        build()
        );
    }

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_5_if_api2x_code_eq_DT_0000X_and_biser_code_eq_DT_OK() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node5");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_0000X").
                        addCompany(123456).
                        addObject("DebtPack").
                        addParameters(data).
                        addPoint("1").
                        addSession("session").
                        build()
        );
    }

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_6_if_both_codes_eq_DT_0000X() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node6");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_0000X").
                        addCompany(123456).
                        addObject("DebtPack").
                        addParameters(data).
                        addPoint("1").
                        addSession("session").
                        build()
        );
    }

    @Test(groups = "stress", invocationCount = 10000, threadPoolSize = 10)
    public void put_message_to_node_7_if_api2x_code_eq_DT_0000X1_and_biser_code_eq_DT_0000X2() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        data.put("node", "node7");

        BiserConveyorClient.newInstance().send(
                BCRequest.Builder.create().
                        addType(BCRequest.Type.SEARCH).
                        addCode("DT_0000X1").
                        addCompany(123456).
                        addObject("DebtPack").
                        addParameters(data).
                        addPoint("1").
                        addSession("session").
                        build()
        );
    }
}
