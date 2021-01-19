package com.exercise.cfs.dto;

import com.exercise.cfs.model.CallForService;

import java.util.Date;
import java.util.UUID;

public class CFSDto {
    private UUID id;
    private String number;
    private String code;
    private Date eventTime;
    private Date dispatchTime;

    public static CFSDto from(CallForService cfs) {
        CFSDto dto = new CFSDto();
        dto.setId(cfs.getId());
        dto.setNumber(cfs.getNumber());
        dto.setCode(cfs.getCode());
        dto.setEventTime(cfs.getEventTime());
        dto.setDispatchTime(cfs.getDispatchTime());
        return dto;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }
}
