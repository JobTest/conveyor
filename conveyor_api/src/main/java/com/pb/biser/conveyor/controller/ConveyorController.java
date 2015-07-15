package com.pb.biser.conveyor.controller;

import com.pb.biser.conveyor.controller.entity.*;
import com.pb.biser.conveyor.service.BiserService;
import com.pb.biser.conveyor.service.DebtPackService;
import com.pb.biser.conveyor.service.DebugService;
import com.pb.bpln.core.exeption.BaseException;
import com.pb.timing.MDCSession;
import org.jboss.logging.MDC;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.report.impl.DefaultDifferenceView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by diver on 5/11/15.
 */
@RestController("/rest")
public class ConveyorController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS");
    private static final String INTERFACE_NAME = "CONV_BISER";
    private static final String LOG_MSG_DELIMITER = "]|[";
    private static final String SERVER_NAME = "server_name";
    private static final String CONV = "conv";
    private static final String TO_INPUT = "T0_INPUT";
    private static final String TO_OUTPUT = "T0_OUTPUT";
    @Autowired
    private DebtPackService debtPackService;
    @Autowired
    private DebugService debugService;
    @Autowired
    private BiserService biserService;


    public void setDebtPackService(DebtPackService debtPackService) {
        this.debtPackService = debtPackService;
    }

    public void setBiserService(BiserService biserService) {
        this.biserService = biserService;
    }

    public void setDebugService(DebugService debugService) {
        this.debugService = debugService;
    }

    @RequestMapping(value = "/call/search", method = RequestMethod.POST)
    public
    @ResponseBody
    Response search(@RequestBody Request request, @RequestHeader(required = false) String debug) throws Exception {
        initLoggerData(request);
        printLog(request, TO_INPUT);
        Response response = null;

        if (debugService.getSecretKey().equals(debug)) {
            return debugService.getDebtPack(request.getBiserRequest());
        }

        Object biserPack = biserService.call(request.biserRequest);
        DiffRequest diffRequest = request.getDiffRequest();

        try {
            if (diffRequest != null && isNotBlank(diffRequest.getApi2xPack())) {
                Object debtPack = debtPackService.deserialize(diffRequest.getApi2xPack());

                ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(ReflectionComparatorMode.LENIENT_ORDER);
                Difference difference = comparator.getDifference(debtPack, biserPack, false);
                if (difference == null) {
                    response = new Response(new BiserResponse(diffRequest.getApi2xPack()));
                    return response;
                }
            }
            String serializedObject = debtPackService.serialize(biserPack);
            response = new Response(new BiserResponse(serializedObject));
            return response;
        } finally {
            printLog(response, TO_OUTPUT);
        }
    }

    private void initLoggerData(Request request) {
        MDCSession.setInterface(INTERFACE_NAME);
        StringBuilder builder = new StringBuilder();

        builder.append(request.getBiserRequest().getSession()).append(LOG_MSG_DELIMITER)
                .append(request.getSys().getRef()).append(LOG_MSG_DELIMITER)
                .append(request.getSys().getObj_id()).append(LOG_MSG_DELIMITER)
                .append(request.getSys().getConv_id()).append(LOG_MSG_DELIMITER)
                .append(request.getSys().getNode_id());

        MDC.put(SERVER_NAME, CONV);
        MDCSession.setSessionId(builder.toString());
    }

    private void printLog(Object data, String logger) {
        StringBuilder builder = new StringBuilder();

        builder.append(logger).append(LOG_MSG_DELIMITER)
                .append(MDCSession.getSessionId()).append(LOG_MSG_DELIMITER)
                .append(INTERFACE_NAME).append(LOG_MSG_DELIMITER)
                .append(LocalDateTime.now().format(formatter)).append(LOG_MSG_DELIMITER)
                .append(data.toString());

        LoggerFactory.getLogger(logger).info(builder.toString());
    }

    @RequestMapping(value = "/diff", method = RequestMethod.POST)
    public
    @ResponseBody
    DiffResponse diff(@RequestBody Request request) throws Exception {
        DiffRequest diffRequest = request.getDiffRequest();
        if (diffRequest == null) throw new IllegalArgumentException("Empty request!");
        Object expected = debtPackService.deserialize(diffRequest.getApi2xPack());
        Object actual = debtPackService.deserialize(diffRequest.getBiserPack());

        ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(ReflectionComparatorMode.LENIENT_ORDER);
        Difference difference = comparator.getDifference(expected, actual, false);

        String view = new DefaultDifferenceView().createView(difference);
        return new DiffResponse(view);
    }

    @ExceptionHandler(BaseException.class)
    public Response throwJsonError(BaseException ex) {
        Response response = new Response(new BiserResponse(ex.getCode(), ex.getMessage()));
        printLog(response, TO_OUTPUT);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public Response throwJsonError(Exception ex) {
        Response response = new Response(new BiserResponse("DT_SYS", ex.getMessage()));
        printLog(response, TO_OUTPUT);
        return response;
    }
}
