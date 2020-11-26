package com.example.tracktrigger32;

import java.util.Date;

public class ReminderWork {

    //@PrimaryKey(autoGenerate = true)
    //@NonNull
    //public int id;

    String message;
    Date remindDate;

    /*public ReminderWork(String message, Date remindDate) {
        this.message = message;
        this.remindDate = remindDate;
    }*/

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
