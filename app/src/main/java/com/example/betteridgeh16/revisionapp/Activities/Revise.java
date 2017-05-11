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
    String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView Answer = (TextView) findViewById(R.id.Answer);


        subjectIndex = getIntent().getIntExtra("Subject Index", 1);
        subject = FileManipulation.fileToStringArray(Revise.this,"courses")[subjectIndex];

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.right_arrow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, (Answer.getText().toString().equals(answer))? "Correct":"Incorrect", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                generateQuestion();
            }//TODO: Go to the next question
        });

        if (new File(getFilesDir(), subject+"Specification.txt").exists()){
            Log.i("Specification for tests", "Exists");
            generateQuestion();

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

    private void generateQuestion(){
        String randomLine;
        Integer[] locationsOfSpaces;



        do{
            randomLine = FileManipulation.getRandomLineFromTextFile(Revise.this, subject+"Specification");
            locationsOfSpaces = StringManipulation.arrayOfPositionsOfCharacter(randomLine, ' ');

        }while (randomLine.equals("")||(locationsOfSpaces.length < 2));


        Log.i("Random line", randomLine);

        Integer numberOfSpaces = locationsOfSpaces.length;
        Log.i("NumberOfSpaces", numberOfSpaces.toString());

        Integer index =(int)(Math.random()*(locationsOfSpaces.length-2));
        Log.i("Index", index.toString());


        answer = randomLine.substring(locationsOfSpaces[index]+1, locationsOfSpaces[index+1]);
        Log.i("Word", answer);

        TextView Question = (TextView) findViewById(R.id.Question);
        Question.setText(StringManipulation.AsterixiseBetweenBound(randomLine, locationsOfSpaces[index], locationsOfSpaces[index+1]));

    }



}
