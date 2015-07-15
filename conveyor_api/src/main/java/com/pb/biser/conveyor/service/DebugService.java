package com.pb.biser.conveyor.service;

import com.pb.biser.conveyor.controller.entity.BiserRequest;
import com.pb.biser.conveyor.controller.entity.Response;
import com.pb.biser.conveyor.properties.DebugProperties;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by diver on 5/19/15.
 */
@Service
public class DebugService {

    private static final String NODE = "node";

    @Autowired
    private DebugProperties debugProperties;
    @Autowired
    private DebtPackService debtPackService;

    public Response getDebtPack(BiserRequest biserRequest) {
        String node = biserRequest.getParameters().get(NODE);
        if (StringUtils.isBlank(node)) {
            throw new NullPointerException("Name of the node does not specified");
        }
        String responseString = debugProperties.getProperty(node);
        if (StringUtils.isBlank(responseString)) {
            throw new NullPointerException("Response for current node does not specified");
        }
        JSONObject responseJSON = JSONObject.fromObject(responseString);
        Response response = (Response) JSONObject.toBean(responseJSON, Response.class);
        String body = response.getBiserResponse().getBody();
        String serializedBody = debtPackService.serialize(body);
        response.getBiserResponse().setBody(serializedBody);
        return response;
    }

    public String getSecretKey() {
        return debugProperties.getSecretKey();
    }
}
