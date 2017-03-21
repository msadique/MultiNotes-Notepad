package com.sadique.multinotes;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class QuickNotes  implements Serializable {

    private int ID;
    private String title;
    private String time;
    private String quickNotes;

    public QuickNotes(int id) {
        this.title = "";
        DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
        String date = df.format(Calendar.getInstance().getTime());
        this.time = date;
        this.quickNotes = "";
        this.ID = id;
    }

    public QuickNotes() {
    }

    public QuickNotes(int id,String title,String time,String notes) {
        this.title = title;
        DateFormat df = new SimpleDateFormat("EEE MMM d, h:mm:ss a");
        String date = df.format(Calendar.getInstance().getTime());
        this.time = time;
        this.quickNotes = notes;
        this.ID = id;
    }

    public void setID(int id) {
        this.ID = id;
    }
    public int getID() { return ID;  }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getQuickNotes() {
        return quickNotes;
    }
    public void setQuickNotes(String quickNotes) {
        this.quickNotes = quickNotes;
    }

    @Override
    public String toString() {
        return title + " (" + time + "), " + quickNotes;
    }
}
