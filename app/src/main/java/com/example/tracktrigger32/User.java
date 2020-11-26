package com.example.tracktrigger32;

public class User {

    private String name,telNum, mailID, gender;

    public User(String name, String telNum, String mailID, String gender) {
        this.name = name;
        this.telNum = telNum;
        this.mailID = mailID;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
