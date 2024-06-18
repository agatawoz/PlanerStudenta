package com.example.login_page;

public class Grade {
    private int dbID;
    private String name;
    private double value;

    public Grade(int dbID, String name, double value){
        this.dbID = dbID;
        this.name = name;
        this.value = value;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
