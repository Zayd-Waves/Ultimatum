package com.electricpanda.ultimatum.entities;

import java.io.Serializable;
import java.util.Date;

public class Pact implements Serializable {

    private String pactId;
    private String habit;
    private Date startDate;
    private Date endDate;
    private int length;
    private int stakes;

    // Constructors
    private Pact() { }
    public Pact(String pId, String habit, Date start, Date end, int length, int stakes) {
        this.pactId = pId;
        this.habit  = habit;
        this.startDate = start;
        this.endDate = end;
        this.length = length;
        this.stakes = stakes;
    }

    // Getters
    public String getPactId() {
        return pactId;
    }
    public String getHabit() {
        return habit;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public int getLength() {
        return length;
    }
    public int getStakes() {
        return stakes;
    }

    // Setters
    public void setPactId(String pactId) {
        this.pactId = pactId;
    }
    public void setHabit(String habit) {
        this.habit = habit;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setStakes(int stakes) {
        this.stakes = stakes;
    }

}
