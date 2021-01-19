package com.exercise.cfs.controller;

import com.exercise.cfs.dto.*;
import com.exercise.cfs.model.CallForService;
import com.exercise.cfs.service.CFSService;
import com.exercise.cfs.util.DateFormatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/cfs")
public class CFSController {

    private final CFSService cfsService;

    public CFSController(CFSService cfsService) {
        this.cfsService = cfsService;
    }

    @ResponseBody
    @GetMapping
    public PageResponse<CFSDto> search(@RequestParam(value = "dispatcherId") UUID dispatcherId,
                                       @RequestParam(value = "fromTime", required = false) String fromTime,
                                       @RequestParam(value = "toTime", required = false) String toTime,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                       @RequestParam(value = "sortBy", required = false, defaultValue = "eventTime") String field,
                                       @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") Order order) throws ParseException {
        TimeRange timeRange = new TimeRange(DateFormatUtil.parse(fromTime), DateFormatUtil.parse(toTime));
        Page page = new Page(pageSize, pageNum);
        Sort sort = new Sort(field, order);

        PageResponse<CallForService> response = cfsService.search(dispatcherId, timeRange, page, sort);
        List<CallForService> content = response.getContent();
        List<CFSDto> cfsDtoItems = content.stream().map(CFSDto::from).collect(Collectors.toList());

        return new PageResponse<>(response.getTotalElements(), response.getTotalPages(), cfsDtoItems);
    }

}
