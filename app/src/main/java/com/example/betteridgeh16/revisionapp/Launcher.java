package com.example.betteridgeh16.revisionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        android.os.Debug.waitForDebugger();



        Intent intent = new Intent(Launcher.this, Home.class);
        startActivity(intent);
    }
}
