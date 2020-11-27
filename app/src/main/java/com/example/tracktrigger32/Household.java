package com.example.tracktrigger32;

public class Household {

    String hhName, hhpwd, MemID, Mem1ID, Mem2ID, Mem3ID;
    Boolean loggedIn;

    public Household(){

    }

    public Household(String hhName, String hhpwd, String MemID, Boolean loggedIn) {
        this.hhName = hhName;
        this.hhpwd = hhpwd;
        this.MemID = MemID;
        this.loggedIn = loggedIn;
    }

    public String getHhName() {
        return hhName;
    }

    public void setHhName(String hhName) {
        this.hhName = hhName;
    }

    public String getHhpwd() {
        return hhpwd;
    }

    public void setHhpwd(String hhpwd) {
        this.hhpwd = hhpwd;
    }

    public String getMemID() {
        return MemID;
    }

    public void setMemID(String MemID) {
        this.MemID = MemID;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getMem1ID() {
        return Mem1ID;
    }

    public void setMem1ID(String mem1ID) {
        Mem1ID = mem1ID;
    }

    public String getMem2ID() {
        return Mem2ID;
    }

    public void setMem2ID(String mem2ID) {
        Mem2ID = mem2ID;
    }

    public String getMem3ID() {
        return Mem3ID;
    }

    public void setMem3ID(String mem3ID) {
        Mem3ID = mem3ID;
    }
}
