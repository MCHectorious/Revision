package com.example.betteridgeh16.revisionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);


        //String examBoard = getIntent().getStringExtra("ExamBoard");


        (new Title()).execute();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent intent = new Intent(Courses.this, Home.class);
                int itemPosition = position;
                intent.putExtra("Course", courses[position]);
                startActivity(intent);


            }

        });

*/


    }
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
        //String[] courses;

        List<String> CourseList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Courses.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
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
                    if (e.attr("href").contains("subjects")){
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
            TextView textView = (TextView) findViewById(R.id.tv);
            textView.setText(title);
            Log.i("hi",Integer.toString(CourseList.size()));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Courses.this, android.R.layout.simple_list_item_1, android.R.id.text1, CourseList);
            ListView listView = (ListView) findViewById(R.id.List3);
            listView.setAdapter(adapter);

            mProgressDialog.dismiss();
        }
    }
}
