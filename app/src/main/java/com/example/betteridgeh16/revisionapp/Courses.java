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
        Elements elements;
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
                Document document = Jsoup.connect("http://www.aqa.org.uk/subjects").timeout(10000).get();
                // Get the html document title
                elements = document.select("a[href]");

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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Courses.this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, CourseList);
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

                    for (Element element:elements){
                        if(element.text().equals(CourseList.toArray(new String[0])[position])){
                            subject = element.attr("href");
                        }
                    }


                    (new ExactSubject()).execute();
                }

            });

            mProgressDialog.dismiss();



        }
    }
    private class ExactSubject extends AsyncTask<Void, Void, Void> {

        //String[] courses;

        List<String> CourseList = new ArrayList<>();
        List<String> WebsiteList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Courses.this);
            mProgressDialog.setTitle("Getting a list of qualifications");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        private void addToArrayLists(Element e){
            CourseList.add(e.text());
            CourseList.add(e.attr("href"));

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(subject).timeout(15000).get();
                // Get the html document title

                Elements elementsFromFormat1 = document.select("div[class=c-column-wrapper ie8-padding-fix").select("a[href]");
                Elements elementsFromFormat2 = document.select("div[class=listSpecs clearfix").select("a[href]");

                for (Element e:elementsFromFormat1){addToArrayLists(e);}
                for (Element e:elementsFromFormat2){if(!e.text().equals("")){addToArrayLists(e);}}
                if(CourseList.size()==0){
                        Log.i("None Found","Yes");
                        CourseList.add("No courses were found. Sorry.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            //Log.i("size",Integer.toString(CourseList.size()));
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
                    String website = WebsiteList.toArray(new String[0])[position];

                    FileManipulation.writeToFile(subject+"\t"+website,Courses.this);
                    intent.putExtra("subjectListUpdated", true);

                }

            });

            mProgressDialog.dismiss();
            //TODO: use http://tika.apache.org/ to extract text from pdf or use http://www.rgagnon.com/javadetails/java-extract-text-from-a-pdf.html



        }
    }
}
