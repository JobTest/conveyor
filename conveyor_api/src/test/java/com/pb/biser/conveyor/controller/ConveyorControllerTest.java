package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.controller.entity.*;
import com.pb.biser.conveyor.dao.entity.SearchRequestAttributes;
import com.pb.biser.conveyor.service.*;
import com.pb.bpln.cashier.bridge.debt.exceptions.IllegalSearchParamsException;
import com.pb.bpln.cashier.core.debt.search.DebtPackI;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * @author Alexander Bondarenko
 *         Date: 5/19/15
 *         Time: 9:14 AM
 */
public class ConveyorControllerTest {

    private ConveyorController controller;
    private BiserService biserService;
    private DebtPackService packService;
    private DebugService debugService;

    @BeforeMethod
    public void setUp() throws Exception {
        controller = new ConveyorController();

        biserService = mock(BiserService.class);
        packService = mock(DebtPackService.class);
        debugService = mock(DebugService.class);

        controller.setBiserService(biserService);
        controller.setDebtPackService(packService);
        controller.setDebugService(debugService);
    }

    @Test
    public void shouldCallBiserServiceSuccessfully() throws Exception {
        Request request = new Request();

        BiserRequest biserRequest = new BiserRequest();
        biserRequest.setCompanyId(123456l);
        biserRequest.setPoint(11l);
        Map<String, String> params = new HashMap<>();
        params.put("test", "testValue");
        biserRequest.setParameters(params);
        request.setBiserRequest(biserRequest);
        Sys sys = new Sys();
        sys.setRef("ref");
        sys.setConv_id("conv");
        sys.setNode_id("node");
        sys.setObj_id("obj");
        request.setSys(sys);

        when(debugService.getSecretKey()).thenReturn("123456");
        when(packService.serialize(any(DebtPackI.class))).thenReturn(DebtPackServiceTest.SERIALIZED_DEBT_PACK);

        Response response = controller.search(request, "654321");

        verify(biserService).call(biserRequest);
        assertEquals("DT_OK", response.getBiserResponse().getCode());
        assertEquals("Success response", response.getBiserResponse().getMessage());
        assertEquals(DebtPackServiceTest.SERIALIZED_DEBT_PACK, response.getBiserResponse().getBody());
        assertNull(response.getBiserResponse().getDuration());
        assertNull(response.getBiserResponse().getType());
    }

    @Test
    public void shouldHandleBaseExceptionCorrectly() throws Exception {
        IllegalSearchParamsException exception = new IllegalSearchParamsException();
        Response response = controller.throwJsonError(exception);

        assertEquals("DT_000006", response.getBiserResponse().getCode());
        assertNotNull(response.getBiserResponse().getMessage());
        assertNull(response.getBiserResponse().getBody());
        assertNull(response.getBiserResponse().getDuration());
        assertNull(response.getBiserResponse().getType());
    }

    @Test
    public void shouldHandleExceptionCorrectly() throws Exception {
        NullPointerException exception = new NullPointerException("FAIL!");
        Response response = controller.throwJsonError(exception);

        assertEquals("DT_SYS", response.getBiserResponse().getCode());
        assertEquals("FAIL!", response.getBiserResponse().getMessage());
        assertNull(response.getBiserResponse().getBody());
        assertNull(response.getBiserResponse().getDuration());
        assertNull(response.getBiserResponse().getType());
    }
}