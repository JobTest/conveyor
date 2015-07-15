package com.pb.biser.conveyor.service;

import com.pb.biser.conveyor.controller.entity.BiserRequest;
import com.pb.bpln.cashier.bridge.trigger.PresearchServiceTrigger;
import com.pb.bpln.cashier.bridge.trigger.SearchServiceTrigger;
import com.pb.bpln.cashier.core.debt.search.SearchRequisites;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

/**
 * @author Alexander Bondarenko
 *         Date: 5/18/15
 *         Time: 4:48 PM
 */
public class BiserServiceTest {
    private BiserService service;
    @Mock
    private SearchServiceTrigger searchServiceTrigger;
    @Mock
    private PresearchServiceTrigger presearchServiceTrigger;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new BiserService();
        service.setSearchServiceTrigger(searchServiceTrigger);
        service.setPresearchServiceTrigger(presearchServiceTrigger);
    }

    @Test
    public void shouldCallBiserBridgeWithSearchParamsSuccessfully() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("test0", "testValue0");
        params.put("test1", "testValue1");

        BiserRequest request = new BiserRequest();
        request.setCompanyId(123456l);
        request.setPoint(11l);
        request.setType("SEARCH");
        request.setParameters(params);


        service.call(request);
        ArgumentCaptor<SearchRequisites> requisitesArgumentCaptor = ArgumentCaptor.forClass(SearchRequisites.class);
        ArgumentCaptor<Long> pointArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(searchServiceTrigger).call(requisitesArgumentCaptor.capture(), pointArgumentCaptor.capture());
        SearchRequisites actualRequisites = requisitesArgumentCaptor.getValue();
        assertEquals(params, actualRequisites.getParameters());
        assertNull(actualRequisites.getPresearchID());
        assertEquals(request.getCompanyId(), actualRequisites.getProviderID());
        assertEquals(request.getPoint(), pointArgumentCaptor.getValue());
    }

    @Test
    public void shouldCallPresearchServiceOfBiserBridgeWithSearchParamsSuccessfully() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("test0", "testValue0");
        params.put("test1", "testValue1");

        BiserRequest request = new BiserRequest();
        request.setCompanyId(123456l);
        request.setPoint(11l);
        request.setType("PRESEARCH");
        request.setParameters(params);


        service.call(request);
        ArgumentCaptor<SearchRequisites> requisitesArgumentCaptor = ArgumentCaptor.forClass(SearchRequisites.class);
        ArgumentCaptor<Long> pointArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(presearchServiceTrigger).call(requisitesArgumentCaptor.capture(), pointArgumentCaptor.capture());
        SearchRequisites actualRequisites = requisitesArgumentCaptor.getValue();
        assertEquals(params, actualRequisites.getParameters());
        assertNull(actualRequisites.getPresearchID());
        assertEquals(request.getCompanyId(), actualRequisites.getProviderID());
        assertEquals(request.getPoint(), pointArgumentCaptor.getValue());
    }

    @Test
    public void shouldCallBiserBridgeWithPresearchIdSuccessfully() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("presearchId", "test_id");

        BiserRequest request = new BiserRequest();
        request.setCompanyId(123456l);
        request.setPoint(11l);
        request.setType("SEARCH");
        request.setParameters(params);

        service.call(request);
        ArgumentCaptor<SearchRequisites> requisitesArgumentCaptor = ArgumentCaptor.forClass(SearchRequisites.class);
        ArgumentCaptor<Long> pointArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(searchServiceTrigger).call(requisitesArgumentCaptor.capture(), pointArgumentCaptor.capture());
        SearchRequisites actualRequisites = requisitesArgumentCaptor.getValue();
        assertTrue(actualRequisites.getParameters().isEmpty());
        assertEquals("test_id", actualRequisites.getPresearchID());
        assertEquals(request.getCompanyId(), actualRequisites.getProviderID());
        assertEquals(request.getPoint(), pointArgumentCaptor.getValue());
    }
}