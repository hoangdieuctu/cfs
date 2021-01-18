package com.exercise.cfs.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "cfs")
public class CallForService {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String code;

    @Column(name = "event_time", nullable = false)
    private Date eventTime;

    @Column(name = "dispatch_time")
    private Date dispatchTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency; // belongs to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatcher_id", nullable = false)
    private Dispatcher dispatcher; // creates by

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responder_id", nullable = false)
    private Responder responder; // handles by

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

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Responder getResponder() {
        return responder;
    }

    public void setResponder(Responder responder) {
        this.responder = responder;
    }
}
