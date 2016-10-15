package com.electricpanda.ultimatum.entities;

import java.io.Serializable;

public class Pact implements Serializable {

    private String name;
    private int days;
    private String pactId;
    /* More properties to come. */

    public Pact() { }
    public Pact(String n, int d) {
        name = n;
        days = d;
    }

    public String getName() {
        return name;
    }
    public int getDays() {
        return days;
    }
    public String getPactId() { return pactId; }
    public void setName(String name) {
        this.name = name;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public void setPactId(String pId){ this.pactId = pId; }
}
