package com.example.tracktrigger32;

import java.util.Date;

public class ReminderWork {

    //@PrimaryKey(autoGenerate = true)
    //@NonNull
    //public int id;
    String title;
    String message;
    Date remindDate;



    public ReminderWork(){
        //empty constructor
    }

    public ReminderWork(String title, String message, Date remindDate) {
        this.title = title;
        this.message = message;
        this.remindDate = remindDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    /*public int getId() {
        return id;
    }*/

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

   /*public void setId(int id) {
        this.id = id;
    }*/
}
