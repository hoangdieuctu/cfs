package com.exercise.cfs.service;

import com.exercise.cfs.model.Agency;
import com.exercise.cfs.model.CallForService;
import com.exercise.cfs.model.Dispatcher;
import com.exercise.cfs.model.Responder;
import com.exercise.cfs.repository.AgencyRepository;
import com.exercise.cfs.repository.CallForServiceRepository;
import com.exercise.cfs.repository.DispatcherRepository;
import com.exercise.cfs.repository.ResponderRepository;
import com.exercise.cfs.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Collections;
import java.util.UUID;

@Service
@ConditionalOnProperty(
        value = "data.generate",
        havingValue = "true")
public class DataGeneratorService {

    private static Logger logger = LoggerFactory.getLogger(DataGeneratorService.class);

    @Value("${dispatcher.id}")
    private String dispatcherId;

    private final CallForServiceRepository callForServiceRepository;
    private final AgencyRepository agencyRepository;
    private final DispatcherRepository dispatcherRepository;
    private final ResponderRepository responderRepository;

    public DataGeneratorService(CallForServiceRepository callForServiceRepository,
                                AgencyRepository agencyRepository,
                                DispatcherRepository dispatcherRepository,
                                ResponderRepository responderRepository) {
        this.callForServiceRepository = callForServiceRepository;
        this.agencyRepository = agencyRepository;
        this.dispatcherRepository = dispatcherRepository;
        this.responderRepository = responderRepository;
    }

    @PostConstruct
    public void generate() throws ParseException {
        logger.info("Generating data...");
        Agency helena = createAgency("Helena", "The first customer");

        Dispatcher tom = createDispatcher("Tom", "Main responsibility in Helena", helena);

        Responder harry = createResponder("Harry", "HEL_001", helena);
        Responder tobias = createResponder("Tobias", "HEL_002", helena);

        createCfs("3234019", "SMO", "2021-01-10 07:36:04", "2021-01-10 10:30:00", helena, tom, harry);
        createCfs("3234775", "CAR", "2021-01-13 08:10:05", "2021-01-13 08:15:00", helena, tom, tobias);
        createCfs("3234842", "PUB", "2021-01-17 10:00:05", "2021-01-17 11:15:00", helena, tom, tobias);
        logger.info("Complete generate data");
    }

    private Agency createAgency(String name, String description) {
        Agency agency = new Agency();
        agency.setId(UUID.randomUUID());
        agency.setDescription(description);
        agency.setName(name);
        agencyRepository.save(agency);
        return agency;
    }

    private Dispatcher createDispatcher(String name, String description, Agency agency) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setAgency(agency);
        dispatcher.setId(UUID.fromString(dispatcherId));
        dispatcher.setName(name);
        dispatcher.setDescription(description);
        dispatcherRepository.save(dispatcher);
        return dispatcher;
    }

    private Responder createResponder(String name, String code, Agency agency) {
        Responder responder = new Responder();
        responder.setAgency(agency);
        responder.setId(UUID.randomUUID());
        responder.setName(name);
        responder.setCode(code);
        responderRepository.save(responder);
        return responder;
    }

    private CallForService createCfs(String number, String code,
                                     String eventTime, String dispatchTime,
                                     Agency agency, Dispatcher dispatcher, Responder responder) throws ParseException {
        CallForService cfs = new CallForService();
        cfs.setId(UUID.randomUUID());
        cfs.setNumber(number);
        cfs.setCode(code);
        cfs.setAgency(agency);
        cfs.setDispatcher(dispatcher);
        cfs.setResponders(Collections.singletonList(responder));
        cfs.setEventTime(DateFormatUtil.parse(eventTime));
        cfs.setDispatchTime(DateFormatUtil.parse(dispatchTime));
        callForServiceRepository.save(cfs);
        return cfs;
    }
}
