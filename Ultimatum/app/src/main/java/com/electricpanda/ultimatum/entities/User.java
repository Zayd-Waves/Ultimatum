package com.electricpanda.ultimatum.entities;


public class User {

    private String username;

    public User(){ }
    public User(String n, String d) { this.username = n; }

    public String getName(){ return username; }
    public void setName(String name) { this.username = name; }
}
