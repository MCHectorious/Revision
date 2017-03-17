package com.example.betteridgeh16.revisionapp.Utils;

/**
 * Created by betteridgeh16 on 2/28/2017.
 */

public class Subject {
    public int icon;
    public String subject,date,examboard,qualification;

    public Subject() {
        super();
    }

    public Subject(int icon, String subject, String examboard, String qualification,  String date ) {
        super();
        this.icon = icon;
        this.qualification = qualification;
        this.examboard = examboard;
        this.date = date;
        this.subject = subject;
    }
}
