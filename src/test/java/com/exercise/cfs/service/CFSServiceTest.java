package com.exercise.cfs.service;

import com.exercise.cfs.dto.*;
import com.exercise.cfs.exception.CFSException;
import com.exercise.cfs.model.Agency;
import com.exercise.cfs.model.CallForService;
import com.exercise.cfs.model.Dispatcher;
import com.exercise.cfs.model.Responder;
import com.exercise.cfs.repository.AgencyRepository;
import com.exercise.cfs.repository.CallForServiceRepository;
import com.exercise.cfs.repository.DispatcherRepository;
import com.exercise.cfs.repository.ResponderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CFSServiceTest {

    @Autowired
    private CFSService cfsService;

    @Autowired
    private CallForServiceRepository callForServiceRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private DispatcherRepository dispatcherRepository;

    @Autowired
    private ResponderRepository responderRepository;

    private UUID responderId1 = UUID.randomUUID();
    private UUID responderId2 = UUID.randomUUID();

    private UUID cfsId1 = UUID.randomUUID();
    private UUID cfsId2 = UUID.randomUUID();
    private UUID cfsId3 = UUID.randomUUID();
    private UUID cfsId4 = UUID.randomUUID();

    private UUID dispatcherId = UUID.randomUUID();

    private Date getDateInDiffCurrentTimeInMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    @BeforeEach
    public void setup() {
        Agency agency = new Agency();
        agency.setId(UUID.randomUUID());
        agency.setName("Sample Agency");
        agencyRepository.save(agency);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setAgency(agency);
        dispatcher.setId(dispatcherId);
        dispatcher.setName("Sample Dispatcher");
        dispatcherRepository.save(dispatcher);

        ////----////
        Responder responder1 = new Responder();
        responder1.setAgency(agency);
        responder1.setId(responderId1);
        responder1.setName("Sample Responder 1");
        responder1.setCode("C001");
        responderRepository.save(responder1);

        Responder responder2 = new Responder();
        responder2.setAgency(agency);
        responder2.setId(responderId2);
        responder2.setName("Sample Responder 2");
        responder2.setCode("C002");
        responderRepository.save(responder2);

        ////----////
        Date last30mins = getDateInDiffCurrentTimeInMinutes(-30);
        Date last29mins = getDateInDiffCurrentTimeInMinutes(-29);

        CallForService cfs1 = new CallForService();
        cfs1.setId(cfsId1);
        cfs1.setNumber("001");
        cfs1.setCode("F001");
        cfs1.setAgency(agency);
        cfs1.setDispatcher(dispatcher);
        cfs1.setResponders(Collections.singletonList(responder1)); // responder 1
        cfs1.setEventTime(last30mins);
        cfs1.setDispatchTime(last29mins);
        callForServiceRepository.save(cfs1);

        ////----////
        Date last20mins = getDateInDiffCurrentTimeInMinutes(-20);
        Date last19mins = getDateInDiffCurrentTimeInMinutes(-19);

        CallForService cfs2 = new CallForService();
        cfs2.setId(cfsId2);
        cfs2.setNumber("002");
        cfs2.setCode("F002");
        cfs2.setAgency(agency);
        cfs2.setDispatcher(dispatcher);
        cfs2.setResponders(Collections.singletonList(responder2)); // responder 2
        cfs2.setEventTime(last20mins);
        cfs2.setDispatchTime(last19mins);
        callForServiceRepository.save(cfs2);

        ////----////
        Date last10mins = getDateInDiffCurrentTimeInMinutes(-10);
        Date last9mins = getDateInDiffCurrentTimeInMinutes(-9);

        CallForService cfs3 = new CallForService();
        cfs3.setId(cfsId3);
        cfs3.setNumber("003");
        cfs3.setCode("F003");
        cfs3.setAgency(agency);
        cfs3.setDispatcher(dispatcher);
        cfs3.setResponders(Collections.singletonList(responder2)); // responder 2
        cfs3.setEventTime(last10mins);
        cfs3.setDispatchTime(last9mins);
        callForServiceRepository.save(cfs3);

        ////----////
        Date last5mins = getDateInDiffCurrentTimeInMinutes(-5);
        Date last4mins = getDateInDiffCurrentTimeInMinutes(-4);

        CallForService cfs4 = new CallForService();
        cfs4.setId(cfsId4);
        cfs4.setNumber("004");
        cfs4.setCode("F004");
        cfs4.setAgency(agency);
        cfs4.setDispatcher(dispatcher);
        cfs4.setResponders(Collections.singletonList(responder2)); // responder 2
        cfs4.setEventTime(last5mins);
        cfs4.setDispatchTime(last4mins);
        callForServiceRepository.save(cfs4);
    }

    @Test
    public void testSearchWithDispatcherNotFound() {
        UUID id = UUID.randomUUID();
        try {
            cfsService.search(id, null, null, null);
            Assertions.fail("This should throw an exception when dispatcher not found");
        } catch (CFSException ex) {
            Assertions.assertEquals("Dispatcher not found: " + id, ex.getMessage());
        }
    }

    @Test
    public void testSearchByDefault() {
        PageResponse<CallForService> response = cfsService.search(dispatcherId, null, null, null);
        Assertions.assertEquals(4, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());
        List<CallForService> items = response.getContent();
        Assertions.assertEquals(4, items.size());
        Assertions.assertEquals(cfsId1, items.get(0).getId());
        Assertions.assertEquals(cfsId2, items.get(1).getId());
        Assertions.assertEquals(cfsId3, items.get(2).getId());
        Assertions.assertEquals(cfsId4, items.get(3).getId());
    }

    @Test
    public void testSearchByTimeBetween() {
        Date last25mins = getDateInDiffCurrentTimeInMinutes(-25);
        Date last7mins = getDateInDiffCurrentTimeInMinutes(-7);
        TimeRange timeRange = new TimeRange(last25mins, last7mins);

        PageResponse<CallForService> response = cfsService.search(dispatcherId, timeRange, null, null);
        Assertions.assertEquals(2, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());
        List<CallForService> items = response.getContent();
        Assertions.assertEquals(2, items.size());
        Assertions.assertEquals(cfsId2, items.get(0).getId());
        Assertions.assertEquals(cfsId3, items.get(1).getId());
    }

    @Test
    public void testSearchByTimeAfter() {
        Date last25mins = getDateInDiffCurrentTimeInMinutes(-25);
        TimeRange timeRange = new TimeRange(last25mins, null);

        PageResponse<CallForService> response = cfsService.search(dispatcherId, timeRange, null, null);
        Assertions.assertEquals(3, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());
        List<CallForService> items = response.getContent();
        Assertions.assertEquals(3, items.size());
        Assertions.assertEquals(cfsId2, items.get(0).getId());
        Assertions.assertEquals(cfsId3, items.get(1).getId());
        Assertions.assertEquals(cfsId4, items.get(2).getId());
    }

    @Test
    public void testSearchByTimeBefore() {
        Date last7mins = getDateInDiffCurrentTimeInMinutes(-7);
        TimeRange timeRange = new TimeRange(null, last7mins);

        PageResponse<CallForService> response = cfsService.search(dispatcherId, timeRange, null, null);
        Assertions.assertEquals(3, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());
        List<CallForService> items = response.getContent();
        Assertions.assertEquals(3, items.size());
        Assertions.assertEquals(cfsId1, items.get(0).getId());
        Assertions.assertEquals(cfsId2, items.get(1).getId());
        Assertions.assertEquals(cfsId3, items.get(2).getId());
    }

    @Test
    public void testSearchByTimeBeforeWithPaging() {
        Date last7mins = getDateInDiffCurrentTimeInMinutes(-7);
        TimeRange timeRange = new TimeRange(null, last7mins);

        Page page = new Page(2, 0);

        PageResponse<CallForService> response = cfsService.search(dispatcherId, timeRange, page, null);
        Assertions.assertEquals(3, response.getTotalElements());
        Assertions.assertEquals(2, response.getTotalPages());
        List<CallForService> items = response.getContent();
        Assertions.assertEquals(2, items.size());
        Assertions.assertEquals(cfsId1, items.get(0).getId());
        Assertions.assertEquals(cfsId2, items.get(1).getId());
    }

    @Test
    public void testSearchByTimeBeforeWithPagingAndSorting() {
        Date last7mins = getDateInDiffCurrentTimeInMinutes(-7);
        TimeRange timeRange = new TimeRange(null, last7mins);

        Page page = new Page(2, 0);
        Sort sort = new Sort("code", Order.desc);

        PageResponse<CallForService> response = cfsService.search(dispatcherId, timeRange, page, sort);
        Assertions.assertEquals(3, response.getTotalElements());
        Assertions.assertEquals(2, response.getTotalPages());
        List<CallForService> items = response.getContent();
        Assertions.assertEquals(2, items.size());
        Assertions.assertEquals(cfsId3, items.get(0).getId());
        Assertions.assertEquals(cfsId2, items.get(1).getId());
    }
}
