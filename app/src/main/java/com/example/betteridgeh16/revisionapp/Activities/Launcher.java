package com.example.betteridgeh16.revisionapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;
import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.GraphicsManipulation;
import com.example.betteridgeh16.revisionapp.Utils.StringManipulation;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


        Intent intent = new Intent(Launcher.this, Home.class);



        //FileManipulation.deleteAll(Launcher.this);

        //Log.i("Error", StringManipulation.trimStringToCourse("General Studies Business"));

        startActivity(intent);

    }
}
