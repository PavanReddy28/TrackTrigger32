package com.example.tracktrigger32;

public class User {

    private String name,telNum, mailID, gender,userID;
    private String hhID, workID, notesID;

    public User() {
    }

    public User(String name, String telNum, String mailID, String userID) {
        this.name = name;
        this.telNum = telNum;
        this.mailID = mailID;
        //this.gender = gender;
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHhID() {
        return hhID;
    }

    public void setHhID(String hhID) {
        this.hhID = hhID;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getNotesID() {
        return notesID;
    }

    /*public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }*/
}
