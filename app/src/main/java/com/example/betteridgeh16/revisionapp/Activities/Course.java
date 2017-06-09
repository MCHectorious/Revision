package com.example.betteridgeh16.revisionapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.CustomExpandableListAdapter;
import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;
import com.example.betteridgeh16.revisionapp.Utils.PDFextraction;
import com.example.betteridgeh16.revisionapp.Utils.PastPaperSeriesObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course extends AppCompatActivity {
    Integer timeoutlength = 50000; //TODO:Base this number on connection speed
    Integer subjectIndex;
    String subject;

    HashMap<String, List<String>> seriesAndPastPapers = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjectIndex = getIntent().getIntExtra("Subject Index", 1);
        subject = FileManipulation.fileToStringArray(Course.this,"courses")[subjectIndex];
        setTitle(subject);


        //TODO:Next thing to work on - downloading past papers


        (new getPastPapersAndMarkSchemes()).execute();
        Log.i("test", "got this far");
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        List<String> expandableListTitle = new ArrayList<>(seriesAndPastPapers.keySet());
        ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, seriesAndPastPapers);
        expandableListView.setAdapter(expandableListAdapter);



        enableOrDisableSpecButton();

        //Log.i("test", "test");//TODO: Fix Github
        //test


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.launcher_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Course.this, Revise.class);
                intent.putExtra("Subject Index", subjectIndex);
                startActivity(intent);
            }
        });





    }

    private void enableOrDisableSpecButton(){
        Button downloadSpecButton = (Button) findViewById(R.id.downloadSpecificationButton);//TODO: Show user the download size
        Log.i("File name is", subject+"Specification.txt");
        if (new File(getFilesDir(), subject+"Specification.txt").exists()){
            downloadSpecButton.setEnabled(false);
            downloadSpecButton.setText("You have already downloaded the Specification for this subject");

            Log.i("Text file", "Does exist");

        }else{
            Log.i("Text file", "Doesn't exist");
            downloadSpecButton.setEnabled(true);
            downloadSpecButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    (new DownloadSpecification()).execute();
                }
            });
        }
    }
    private class DownloadSpecification extends AsyncTask<Void,Void,Void>{
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



                PDFextraction.downloadPDF(PDFwebsite,subject,Course.this);
                PDFextraction.extractTextFromPDF(subject, Course.this);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            mProgressDialog.dismiss();
            enableOrDisableSpecButton();

        }
    }

    private class getPastPapersAndMarkSchemes extends AsyncTask<Void,Void,Void>{
        ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Course.this);
            mProgressDialog.setTitle("Loading Past Papers and Mark Schemes");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }



        @Override
        protected Void doInBackground(Void... params){
            try {
                String website = FileManipulation.fileToStringArray(Course.this,"websites")[subjectIndex]+"/past-papers-and-mark-schemes";

                //ArrayList<PastPaperSeriesObject> pastPaperAndSeries = new ArrayList<>();

                Document document = Jsoup.connect(website).timeout(timeoutlength).get();

                Element accordion = document.select("div[class=accordion-wrap]").first();

                Log.i("Found div", (accordion!=null)? "True":"False");

                Elements series = accordion.select("div[class=toggleizrSet]");

                for (Integer i=0;i<series.size();i++){
                    Element IndividualSeries = series.get(i);

                    String nameOfSeries = IndividualSeries.select("h2[class=title]").first().text();

                    Log.i("Series", nameOfSeries);



                    Elements papers = IndividualSeries.select("div[class=toggleizrPanel");


                    ArrayList<String> pastPapersEachSeries = new ArrayList<>();

                    for (Integer j=0;j<papers.size();j++){





                        String unit = papers.get(j).select("h3[class=panelTitle]").text();


                        Log.i("Paper", unit);
                        pastPapersEachSeries.add(unit);

                    }

                    //pastPaperAndSeries.add(new PastPaperSeriesObject(nameOfSeries, pastPapersEachSeries.toArray(new String[0])));
                    seriesAndPastPapers.put(nameOfSeries,pastPapersEachSeries);

                }

                /*PastPaperSeriesObject[] pastPaperSeriesObjects = pastPaperAndSeries.toArray(new PastPaperSeriesObject[0]);

                for (int examSeriesIndex = 0; examSeriesIndex<pastPaperSeriesObjects.length;examSeriesIndex++){
                    String temp = "";


                    Log.i("Series", pastPaperSeriesObjects[examSeriesIndex].getSeriesName());

                    for (int pastPaperIndex=0; pastPaperIndex<pastPaperSeriesObjects[examSeriesIndex].getPastPapersForThisSeries().length ;pastPaperIndex++){
                        temp = temp.concat(pastPaperSeriesObjects[examSeriesIndex].getPastPapersForThisSeries()[pastPaperIndex]+", ");

                    }

                    Log.i(pastPaperSeriesObjects[examSeriesIndex].getSeriesName(),temp);




                }*/

                Log.i("Past Papers", seriesAndPastPapers.toString());









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
