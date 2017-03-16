package com.example.betteridgeh16.revisionapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.betteridgeh16.revisionapp.FileManipulation;
import com.example.betteridgeh16.revisionapp.R;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        FileManipulation.createFile(Launcher.this, "courses");
        FileManipulation.createFile(Launcher.this, "websites");
        FileManipulation.createFile(Launcher.this, "examboards");
        FileManipulation.createFile(Launcher.this, "qualifications");
        FileManipulation.createFile(Launcher.this, "importantdates");

        //android.os.Debug.waitForDebugger();
        //Log.i("Me","Test");
        //FileManipulation.clearAll(Launcher.this);

        /*FileManipulation.writeToFile("A","courses",Launcher.this);
        FileManipulation.writeToFile("A","websites",Launcher.this);
        FileManipulation.writeToFile("A","examboards",Launcher.this);
        FileManipulation.writeToFile("A" ,"qualifications",Launcher.this);
        FileManipulation.writeToFile("A","importantdates",Launcher.this);

        
        FileManipulation.appendToFile("B","courses",Launcher.this);
        FileManipulation.appendToFile("B","websites",Launcher.this);
        FileManipulation.appendToFile("B","examboards",Launcher.this);
        FileManipulation.appendToFile("B" ,"qualifications",Launcher.this);
        FileManipulation.appendToFile("B","importantdates",Launcher.this);

        */

        Intent intent = new Intent(Launcher.this, Home.class);
        startActivity(intent);
    }
}
