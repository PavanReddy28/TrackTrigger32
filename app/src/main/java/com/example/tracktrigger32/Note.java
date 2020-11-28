package com.example.tracktrigger32;

public class Note {

    private String text, userID;

    public Note(String text, String userID) {
        this.text = text;
        this.userID = userID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
