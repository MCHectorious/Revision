package com.example.betteridgeh16.revisionapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;
import com.example.betteridgeh16.revisionapp.Utils.StringManipulation;

import java.io.File;

public class Revise extends AppCompatActivity {
    Integer subjectIndex;
    String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjectIndex = getIntent().getIntExtra("Subject Index", 1);
        subject = FileManipulation.fileToStringArray(Revise.this,"courses")[subjectIndex];

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.right_arrow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Will go to the next question", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (new File(getFilesDir(), subject+"Specification.txt").exists()){
            Log.i("Specification for tests", "Exists");

            String randomLine = FileManipulation.getRandomLineFromTextFile(Revise.this,  subject+"Specification");
            Log.i("Random line", randomLine);
            Integer numberofSpaces = StringManipulation.arrayOfPositionsOfCharacter(randomLine, ' ').length;
            Log.i("Number of Spaces", numberofSpaces.toString());
            Integer index =(int)(Math.random()*numberofSpaces); //TODO: I was last working here





        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            TextView textView = new TextView(this);
            textView.setText("You currently have no downloaded material for this course."
                    + System.getProperty("line.separator")
                    + "To download some revision material and continue click OKAY");
            textView.setTextColor(Color.WHITE);
            alertDialogBuilder.setView(textView);
            alertDialogBuilder.setCancelable(false).setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Revise.this, Course.class);
                    intent.putExtra("Subject Index", subjectIndex);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

}
