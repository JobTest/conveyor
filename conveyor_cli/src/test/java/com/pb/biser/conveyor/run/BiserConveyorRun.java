package com.pb.biser.conveyor.run;

import com.pb.biser.conveyor.BiserConveyorClient;
import com.pb.biser.conveyor.BiserConveyorException;
import com.pb.biser.conveyor.entity.BCRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by diver on 5/15/15.
 */
public class BiserConveyorRun {

    public static void main(String[] args) throws BiserConveyorException {
        BiserConveyorClient biserConveyorClient = BiserConveyorClient.newInstance();

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("some", "value");


        biserConveyorClient.send(BCRequest.Builder.
                create().
                addCompany(23123).
                addObject("HELLO").
                addParameters(parameters).
                addPoint("1").
                build());
    }
}
