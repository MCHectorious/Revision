package com.example.betteridgeh16.revisionapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Course extends AppCompatActivity {
    Integer timeoutlength = 50000; //TODO:Base this number on connection speed
    Integer subjectIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjectIndex = getIntent().getIntExtra("Subject Index", 1);
        setTitle(FileManipulation.fileToStringArray(Course.this,"courses")[subjectIndex]);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.launcher_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Course.this, Revise.class);
                startActivity(intent);
            }
        });

        Button downloadSpecButton = (Button) findViewById(R.id.downloadSpecificationButton);
        downloadSpecButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                (new DownloadSpecification()).execute();
            }
        });


    }

    private class DownloadSpecification extends AsyncTask<Void,Void,Void>{ //TODO: Need to complete this
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Course.this);
            mProgressDialog.setTitle("Downloading Specification");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }



        @Override
        protected Void doInBackground(Void... params){
            try {
                String website = FileManipulation.fileToStringArray(Course.this,"websites")[subjectIndex];
                Document document = Jsoup.connect(website).timeout(timeoutlength).get();

                Element element = document.select("a[class=bttn downloadBttn bttnG no-icon").first();

                String PDFwebsite = element.attr("href");
                Log.i("PDF website", PDFwebsite);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            mProgressDialog.dismiss();
        }
    }
}
