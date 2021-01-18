package com.exercise.cfs.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "agency")
public class Agency {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agency")
    private List<Dispatcher> dispatchers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agency")
    private List<Responder> responders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agency")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dispatcher> getDispatchers() {
        return dispatchers;
    }

    public void setDispatchers(List<Dispatcher> dispatchers) {
        this.dispatchers = dispatchers;
    }

    public List<Responder> getResponders() {
        return responders;
    }

    public void setResponders(List<Responder> responders) {
        this.responders = responders;
    }

    public List<CallForService> getCallForServices() {
        return callForServices;
    }

    public void setCallForServices(List<CallForService> callForServices) {
        this.callForServices = callForServices;
    }
}
