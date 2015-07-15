package com.pb.biser.conveyor;

import com.pb.biser.conveyor.entity.*;
import com.pb.crm.conveyorapiutils.entity.ConveyorMessage;
import com.pb.crm.conveyorapiutils.entity.ConveyorRequest;
import com.pb.crm.conveyorapiutils.entity.RequestOperation;
import com.pb.crm.conveyorapiutils.utils.HttpManager;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by diver on 5/14/15.
 */
public final class BiserConveyorClient {

    private static final String PARAMETERS = "parameters";
    private static final String COMPANY_ID = "company_id";
    private static final String API_2X_SESSION = "api2x_sid";
    private static final String API_2X_CODE = "api2x_code";
    private static final String DEBT_PACK = "api2x_pack";
    private static final String POINT = "point";
    private static final String TYPE = "type";

    private ConveyorSettingsI conveyorSettings;
    private HttpManager httpManager;

    private BiserConveyorClient() {}

    public void setConveyorSettings(ConveyorSettingsI conveyorSettings) {
        this.conveyorSettings = conveyorSettings;
    }

    public void serHttpManager(HttpManager httpManager) {
        this.httpManager = httpManager;
    }

    private void initializeDefaultSettings() throws BiserConveyorException {
        if (conveyorSettings == null) {
            try {
                conveyorSettings = ConveyorSettings.getDefault();
            } catch (IOException ex) {
                throw new BiserConveyorException(ex.getMessage(), ex);
            }
        }
        if (httpManager == null) {
            int maxCount = conveyorSettings.getMaxCount();
            int connectTimeout = conveyorSettings.getConnectTimeout();
            int readTimeout = conveyorSettings.getReadTimeout();
            httpManager = new HttpManager(maxCount, connectTimeout, readTimeout);
        }
    }

    public void send(BCRequestI request) throws BiserConveyorException {
        initializeDefaultSettings();
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put(PARAMETERS, JSONObject.fromObject(request.getParameters()));
            data.put(COMPANY_ID, request.getCompanyId());
            data.put(API_2X_CODE, request.getCode());
            data.put(DEBT_PACK, request.getObject());
            data.put(POINT, request.getPoint());
            data.put(TYPE, request.getType());
            data.put(API_2X_SESSION, request.getSession());
            String conveyorUID = UUID.randomUUID().toString();
            RequestOperation requestOperation = RequestOperation.create(
                    conveyorSettings.getConveyorId(),
                    conveyorUID,
                    data);
            ConveyorMessage message = ConveyorMessage.request(
                    conveyorSettings.getApiKey(),
                    Arrays.asList(requestOperation)
            );
            ConveyorRequest conveyorRequest = ConveyorRequest.getRequest(
                    conveyorSettings.getUrl(),
                    conveyorSettings.getApiLogin(),
                    message
            );
            throwExceptionIfResponseHasError(conveyorUID, httpManager.send(conveyorRequest));
        } catch (Exception ex) {
            throw new BiserConveyorException(ex.getMessage(), ex);
        }
    }

    private void throwExceptionIfResponseHasError(String conveyorUID, String responseString) throws BiserConveyorException {
        JSONObject jsonObject = JSONObject.fromObject(responseString);
        BCResponse response = (BCResponse) JSONObject.toBean(jsonObject, BCResponse.class);
        if (!"ok".equalsIgnoreCase(response.getRequest_proc())) {
            throw new BiserConveyorException("Неизветсная ошибка взаимодействия с конвейером");
        }
        for (BCResponseOps ops : response.getOps()) {
           if (conveyorUID.equalsIgnoreCase(ops.getId())) {
               if ("error".equalsIgnoreCase(ops.getProc())) {
                   throw new BiserConveyorException(ops.getDescription());
               }
           }
        }
    }

    public static final BiserConveyorClient newInstance() {
        return new BiserConveyorClient();
    }
}
