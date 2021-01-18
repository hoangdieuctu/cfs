package com.exercise.cfs.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "responder")
public class Responder {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "responder")
    private List<CallForService> callForServices;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public List<CallForService> getCallForServices() {
        return callForServices;
    }

    public void setCallForServices(List<CallForService> callForServices) {
        this.callForServices = callForServices;
    }
}
