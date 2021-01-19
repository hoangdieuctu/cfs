package com.exercise.cfs.dto;


public class Sort {
    private String field;
    private Order order;

    public Sort() {
    }

    public Sort(String field, Order order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "{" +
                "field=" + field +
                ", order=" + order +
                "}";
    }
}
