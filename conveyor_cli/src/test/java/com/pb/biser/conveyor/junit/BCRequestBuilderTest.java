package com.pb.biser.conveyor.junit;

import com.pb.biser.conveyor.entity.BCRequest;
import com.pb.biser.conveyor.entity.BCRequestI;
import com.pb.biser.conveyor.run.BiserConveyorRun;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.pb.biser.conveyor.entity.BCRequest.Type.*;

/**
 * Created by diver on 5/14/15.
 */
public class BCRequestBuilderTest {

    @Test
    public void shouldThrowRuntimeIfSearchParametersIsEmpty() {
        try {
            BCRequest.Builder.create().
                    addParameters(null).
                    addSession("session").
                    addCompany(1231).
                    addType(SEARCH).
                    addPoint("1").
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("Отсутствуют поисковые параметры", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeIfCodeIsNull() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");

        try {
            BCRequest.Builder.create().
                    addParameters(parameters).
                    addSession("session").
                    addCompany(1231).
                    addType(SEARCH).
                    addPoint("1").
                    addCode(null).
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("Отсутствует код работы системы", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeIfRequestTypeIsNull() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");

        try {
            BCRequest.Builder.create().
                    addParameters(parameters).
                    addSession("session").
                    addCompany(1231).
                    addPoint("1").
                    addCode("DT_OK").
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("Не указан тип выполняемого запроса", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeIfCompanyIdIsEmpty() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");

        try {
            BCRequest.Builder.create().
                    addParameters(parameters).
                    addCompany(null).
                    addType(SEARCH).
                    addPoint("1").
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("Отсутствует идентификатор компании", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeIfSessionIsEmpty() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");

        try {
            BCRequest.Builder.create().
                    addParameters(parameters).
                    addObject("HELLO").
                    addCompany(433930).
                    addType(SEARCH).
                    addPoint("18").
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("Отсутствует идентификатор сессии системы", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeIfPointIsEmpty() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");

        try {
            BCRequest.Builder.create().
                    addParameters(parameters).
                    addSession("session").
                    addCompany(1231).
                    addType(SEARCH).
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("Отсутствует точка приема", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowRuntimeIfObjectNotSerializable() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");
        parameters.put("phone", "+424530104234");

        try {
            BCRequest.Builder.create().
                    addParameters(parameters).
                    addObject(new BiserConveyorRun()).
                    addCompany(433930).
                    addType(SEARCH).
                    addPoint("18").
                    build();
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertEquals("com.pb.biser.conveyor.run.BiserConveyorRun", ex.getMessage());
        }
    }

    @Test
    public void shouldBuildBCRequestOnSuccessfulState() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");
        parameters.put("phone", "+424530104234");

        BCRequestI request = BCRequest.Builder.create().
                addParameters(parameters).
                addSession("session").
                addObject("DEBT_PACK").
                addCompany(433930).
                addType(SEARCH).
                addPoint("18").
                build();

        Assert.assertEquals(parameters, request.getParameters());
        Assert.assertEquals(new Integer(433930), request.getCompanyId());
        Assert.assertEquals("H4sIAAAAAAAAAFvzloG1hIHTxdUpJD7A0dkbALBvXqoQAAAA", request.getObject());
        Assert.assertEquals("SEARCH", request.getType());
        Assert.assertEquals("DT_OK", request.getCode());
        Assert.assertEquals("18", request.getPoint());
    }

    @Test
    public void shouldBuildBCRequestWithPresearchIdOnSuccessfulState() {
        BCRequestI request = BCRequest.Builder.create().
                addPresearchId("test_id").
                addSession("session").
                addObject("HELLO").
                addCompany(433930).
                addType(PRESEARCH).
                addPoint("18").
                build();

        Assert.assertEquals("test_id", request.getParameters().get("presearchId"));
        Assert.assertEquals(new Integer(433930), request.getCompanyId());
        Assert.assertEquals("H4sIAAAAAAAAAFvzloG1hIHVw9XHxx8A6wmu3wwAAAA=", request.getObject());
        Assert.assertEquals("PRESEARCH", request.getType());
        Assert.assertEquals("DT_OK", request.getCode());
        Assert.assertEquals("18", request.getPoint());
    }

    @Test
    public void shouldBuildBCRequestOnUnsuccessfulState() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("account", "543452000304523");
        parameters.put("phone", "+424530104234");

        BCRequestI request = BCRequest.Builder.create().
                addParameters(parameters).
                addSession("session").
                addCompany(433930).
                addCode("DT_ERR").
                addType(SEARCH).
                addPoint("12").
                build();

        Assert.assertEquals(parameters, request.getParameters());
        Assert.assertEquals(new Integer(433930), request.getCompanyId());
        Assert.assertEquals("session", request.getSession());
        Assert.assertEquals("SEARCH", request.getType());
        Assert.assertEquals("DT_ERR", request.getCode());
        Assert.assertEquals("12", request.getPoint());
        Assert.assertNull(request.getObject());
    }
}
