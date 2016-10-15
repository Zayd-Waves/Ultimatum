package com.electricpanda.ultimatum.entities;

import java.io.Serializable;

public class Pact implements Serializable {

    private String pactId;
    private String habit;
    private int length;
    private int stakes;

    // Constructors
    private Pact() { }
    public Pact(String habit, int length, int stakes) {
        this.habit  = habit;
        this.length = length;
        this.stakes = stakes;
    }

    // Getters
    public String getHabit() { return habit;  }
    public int getLength()   { return length; }
    public int getStakes()   { return stakes; }
    public String getPactId() { return pactId; }

    // Setters
    public void setHabit(String habit) { this.habit = habit;   }
    public void setLength(int length)  { this.length = length; }
    public void setStakes(int stakes)  { this.stakes = stakes; }
    public void setPactId(String pId){ this.pactId = pId; }
}
