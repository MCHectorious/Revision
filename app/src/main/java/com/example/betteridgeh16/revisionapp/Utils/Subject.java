package com.example.betteridgeh16.revisionapp.Utils;

import android.graphics.Bitmap;

/**
 * Created by betteridgeh16 on 2/28/2017.
 */

public class Subject {
    //public Integer icon;
    public String icon,subject,date,examboard,qualification;

    public Subject() {
        super();
    }

    public Subject(String icon, String subject, String examboard, String qualification,  String date ) {
        super();
        this.icon = icon;
        this.qualification = qualification;
        this.examboard = examboard;
        this.date = date;
        this.subject = subject;
    }
}
