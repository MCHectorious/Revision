package com.example.betteridgeh16.revisionapp;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //String[] Courses = new String[] {};
        final Intent intent = new Intent(Home.this, ExamBoards.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.add_white);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        //FileManipulation.writeToFile("hi","courses",Home.this);

        String CoursesString = FileManipulation.fileToString("courses",Home.this);
        Log.i("CoursesString",CoursesString);
        if(CoursesString.equals("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            TextView textView = new TextView(this);
            textView.setText("You currently have no courses. To add courses and continue click OK");
            textView.setTextColor(Color.WHITE);
            alertDialogBuilder.setView(textView);
            alertDialogBuilder.setCancelable(false).setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else{
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(Home.this, android.R.layout.simple_list_item_1, android.R.id.text1, FileManipulation.fileToStringList("courses",Home.this));
            ArrayList<Subject> subjectData = new ArrayList<>();
            String[] subject = FileManipulation.fileToStringList("courses",Home.this).toArray(new String[0]);
            //String[] website = FileManipulation.fileToStringList("websites",Home.this).toArray(new String[0]);
            String[] examboard = FileManipulation.fileToStringList("examboards",Home.this).toArray(new String[0]);
            String[] qualification = FileManipulation.fileToStringList("qualifications",Home.this).toArray(new String[0]);
            String[] importantDate = FileManipulation.fileToStringList("importantdates",Home.this).toArray(new String[0]);
            for(int i = 1;i<=subject.length;i++){
                subjectData.add(new Subject(R.drawable.add_white,subject[i],examboard[i],qualification[i],importantDate[i]));
            }


            //Subject subjectData[] = new Subject[]{
            //        new Subject(R.drawable.add_white,"Biology","OCR","A Level","1st May"),
            //        new Subject(R.drawable.ic_launcher_24dp,"Accounting","AQA","GCSE","12th June"),
            //        new Subject(R.drawable.add_white,"Physics","MEI","A Level","25th June")
            //};
            SubjectAdapter adapter = new SubjectAdapter(Home.this,R.layout.subject_list_item,subjectData.toArray(new Subject[0]));
            ListView listView = (ListView) findViewById(R.id.HomeList);
            listView.setAdapter(adapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
