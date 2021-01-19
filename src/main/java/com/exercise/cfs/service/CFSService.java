package com.exercise.cfs.service;

import com.exercise.cfs.dto.*;
import com.exercise.cfs.exception.CFSException;
import com.exercise.cfs.model.CallForService;
import com.exercise.cfs.model.Dispatcher;
import com.exercise.cfs.repository.CallForServiceRepository;
import com.exercise.cfs.repository.DispatcherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CFSService {

    private static Logger logger = LoggerFactory.getLogger(CFSService.class);

    private static final String EVENT_TIME_FIELD = "eventTime";

    private final CallForServiceRepository callForServiceRepository;
    private final DispatcherRepository dispatcherRepository;

    public CFSService(
            CallForServiceRepository callForServiceRepository,
            DispatcherRepository dispatcherRepository) {
        this.callForServiceRepository = callForServiceRepository;
        this.dispatcherRepository = dispatcherRepository;
    }

    /**
     * Search the call for services
     * - time range
     * - paging: default page = 0 and size = 10
     * - sorting: default asc by event time
     *
     * @param dispatcherId
     * @return
     */
    public PageResponse<CallForService> search(UUID dispatcherId, TimeRange time, Page page, Sort sort) {
        logger.debug("Search dispatcherId={}, time={}, page={}, sort={}", dispatcherId, time, page, sort);

        Dispatcher dispatcher = dispatcherRepository.findById(dispatcherId).orElse(null);
        if (dispatcher == null) {
            throw new CFSException("Dispatcher not found: " + dispatcherId);
        }

        if (page == null) {
            page = new Page(10, 0);
        }
        PageRequest pageable = PageRequest.of(page.getNumber(), page.getSize());

        org.springframework.data.domain.Page<CallForService> response = callForServiceRepository.findAll((Specification<CallForService>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // query by dispatcher
            predicates.add(criteriaBuilder.equal(root.get("dispatcher"), dispatcher));

            // query by time range
            if (time != null) {
                if (time.getFrom() != null && time.getTo() != null) {
                    predicates.add(criteriaBuilder.between(root.get(EVENT_TIME_FIELD), time.getFrom(), time.getTo()));
                } else if (time.getFrom() != null) {
                    predicates.add(criteriaBuilder.greaterThan(root.get(EVENT_TIME_FIELD), time.getFrom()));
                } else if (time.getTo() != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get(EVENT_TIME_FIELD), time.getTo()));
                }
            }

            // sorting
            String field = EVENT_TIME_FIELD;
            Order order = Order.asc;
            if (sort != null) {
                field = (sort.getField() == null ? EVENT_TIME_FIELD : sort.getField());
                order = (sort.getOrder() == null ? Order.asc : sort.getOrder());
            }
            criteriaQuery.orderBy(Order.asc.equals(order) ?
                    criteriaBuilder.asc(root.get(field)) :
                    criteriaBuilder.desc(root.get(field)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        long totalElements = response.getTotalElements();
        int totalPages = response.getTotalPages();
        List<CallForService> content = response.getContent();

        logger.debug("Found {} elements in {} pages", totalElements, totalPages);
        return new PageResponse<>(totalElements, totalPages, content);
    }


}
