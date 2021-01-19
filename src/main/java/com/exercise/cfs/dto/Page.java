package com.exercise.cfs.dto;

public class Page {
    private int size;
    private int number;

    public Page() {
    }

    public Page(int size, int number) {
        this.size = size;
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "{" +
                "size=" + size +
                ", number=" + number +
                "}";
    }
}
