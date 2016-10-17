package com.electricpanda.ultimatum.entities;

import android.content.Context;

import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Pact implements Serializable {

    private String habit;
    private Date startDate;
    private Date endDate;
    private int length;
    private int stakes;
    private ArrayList<String> users = new ArrayList<>();
    private String partnerName;

    /* Attributes that we don't supply at all. */
    private String id;
    private int balance;
    private String leader;
    private String creationDate;

    private String[] firstEntry;
    private String firstEntryUsername;
    private String[] secondEntry;
    private String secondEntryUsername;

    // Constructors
    private Pact() { }
    public Pact(String habit, Date start, Date end, int length, int stakes, ArrayList<String> partners) {
        this.habit  = habit;
        this.startDate = start;
        this.endDate = end;
        this.length = length;
        this.stakes = stakes;
        this.users = partners;
    }
    public Pact(String habit, Date start, Date end, int length, int stakes) {
        this.habit  = habit;
        this.startDate = start;
        this.endDate = end;
        this.length = length;
        this.stakes = stakes;
    }
    public Pact(String habit, Date start, Date end, int length, int stakes, String name) {
        this.habit  = habit;
        this.startDate = start;
        this.endDate = end;
        this.length = length;
        this.stakes = stakes;
        this.partnerName = name;
    }

    // Getters
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
    public ArrayList<String> getUsers() { return users; }
    public String getPartnerName(Context context) {
        String name = "";
        for (int i = 0; i < users.size(); i++) {
            if (!PreferencesManager.getUsername(context).equals(users.get(i))) {
                name = users.get(i);
            }
        }
        return name;
    }
    public String getPartnerName() {
        return partnerName;
    }
    public int getBalance() {
        return balance;
    }
    public String getLeader() {
        return leader;
    }
    public String getId(){ return id; }
    public String getCreationDate() { return creationDate; }
    public String[] getFirstEntry() {
        return firstEntry;
    }
    public String getFirstEntryUsername() {
        return firstEntryUsername;
    }
    public void setFirstEntry(String[] firstEntry) {
        this.firstEntry = firstEntry;
    }
    public void setFirstEntryUsername(String firstEntryUsername) {
        this.firstEntryUsername = firstEntryUsername;
    }
    public String[] getSecondEntry() {
        return secondEntry;
    }
    public void setSecondEntry(String[] secondEntry) {
        this.secondEntry = secondEntry;
    }
    public String getSecondEntryUsername() {
        return secondEntryUsername;
    }
    public void setSecondEntryUsername(String secondEntryUsername) {
        this.secondEntryUsername = secondEntryUsername;
    }

    // Setters
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
    public void setUsers(ArrayList<String> partners) { this.users = partners; }
    public void setUsers(String[] partners) { this.users = new ArrayList<>(); this.users.addAll(Arrays.asList(partners)); }
    public void setPartnerName(String name) {this.partnerName = name; }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void setLeader(String leader) {
        this.leader = leader;
    }
    public void setId(String id) { this.id = id; }
    public void setCreationDate(String cDate) { this.creationDate = cDate; }
}
