package com.electricpanda.ultimatum.entities;

public class Pact {

    private String name;
    private int days;
    /* More properties to come. */

    public Pact() { }

    public String getName() {
        return name;
    }
    public int getDays() {
        return days;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDays(int days) {
        this.days = days;
    }
}
