package com.example.betteridgeh16.revisionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Courses extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);


        //String examBoard = getIntent().getStringExtra("ExamBoard");


        (new Subject()).execute();






    }
    private class Subject extends AsyncTask<Void, Void, Void> {

        //String[] courses;

        List<String> CourseList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Courses.this);
            mProgressDialog.setTitle("Getting List of Subjects");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://www.aqa.org.uk/subjects").get();
                // Get the html document title
                Elements elements = document.select("a[href]");

                for (Element e:elements){
                    if(e.attr("href").contains("subjects/") ){
                        CourseList.add(e.text());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            Log.i("hi",Integer.toString(CourseList.size()));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Courses.this, android.R.layout.simple_list_item_1, android.R.id.text1, CourseList);
            ListView listView = (ListView) findViewById(R.id.List3);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Intent intent = new Intent(Courses.this, Home.class);
                    int itemPosition = position;
                    //intent.putExtra("Course", );
                    //startActivity(intent);
                    Snackbar.make(view,CourseList.toArray(new String[0])[position],Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();

                    subject = CourseList.toArray(new String[0])[position];
                    (new ExactSubject()).execute();
                }

            });

            mProgressDialog.dismiss();



        }
    }
    private class ExactSubject extends AsyncTask<Void, Void, Void> {

        //String[] courses;

        List<String> CourseList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Courses.this);
            mProgressDialog.setTitle("Getting a list of qualifications relating to " + subject);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://www.aqa.org.uk/subjects/"+subject.toLowerCase()).get();
                // Get the html document title
                Elements elements = document.select("a[href]");

                for (Element e:elements){
                    try{
                        Integer examCode = Integer.parseInt(e.attr("href").substring(e.attr("href").indexOf("(") +1, e.attr("href").indexOf(")")));
                        CourseList.add(e.text());
                    }catch (NumberFormatException e1){}

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            Log.i("hi",Integer.toString(CourseList.size()));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Courses.this, android.R.layout.simple_list_item_1, android.R.id.text1, CourseList);
            ListView listView = (ListView) findViewById(R.id.List3);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Intent intent = new Intent(Courses.this, Home.class);
                    int itemPosition = position;
                    //intent.putExtra("Course", );
                    //startActivity(intent);
                    Snackbar.make(view,CourseList.toArray(new String[0])[position],Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();

                    subject = CourseList.toArray(new String[0])[position];

                }

            });

            mProgressDialog.dismiss();



        }
    }
}
