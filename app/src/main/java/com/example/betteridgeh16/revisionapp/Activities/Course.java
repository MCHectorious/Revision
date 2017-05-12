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
import android.widget.TextView;

import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;
import com.example.betteridgeh16.revisionapp.Utils.PDFextraction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class Course extends AppCompatActivity {
    Integer timeoutlength = 50000; //TODO:Base this number on connection speed
    Integer subjectIndex;
    String subject;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjectIndex = getIntent().getIntExtra("Subject Index", 1);
        subject = FileManipulation.fileToStringArray(Course.this,"courses")[subjectIndex];
        setTitle(subject);

        //String courseInfo;
        //String examBoard = FileManipulation.fileToStringArray(Course.this,"examboards")[subjectIndex];
        //courseInfo += (examBoard.equals(""))? examBoard: "";


        //TODO:Next thing to work on - downloading past papers


        (new getPastPapersAndMarkSchemes()).execute();

        enableOrDisableSpecButton();




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
                //PDFextraction.extractTextFromDownloadedPDF(subject,Course.this);
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
            /*TextView textView = (TextView) findViewById(R.id.extractPDFtext);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(extractedText);*/
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


                Document document = Jsoup.connect(website).timeout(timeoutlength).get();

                Element accordion = document.select("div[class=accordion-wrap]").first();

                Log.i("Found div", (accordion!=null)? "True":"False");

                Elements series = accordion.select("div[class=toggleizrSet]");

                for (Integer i=0;i<series.size();i++){
                    Element IndividualSeries = series.get(i);
                    Log.i("Series", IndividualSeries.select("h2[class=title]").first().text());

                    Elements papers = IndividualSeries.select("div[class=toggleizrPanel");



                    for (Integer j=0;j<papers.size();j++){

                        Log.i("Found title", (papers.get(j).select("h3[class=panelTitle]")!=null)? "True":"False");

                        Log.i("Found title V2", (papers.get(j).select("h3[class=panelTitle]").select("a[class=togglePanel show]")!=null)? "True":"False");

                        Boolean b =papers.get(j).select("h3[class=panelTitle]").select("a[class=togglePanel show]").hasText();
                        Log.i("Has text", b.toString() );


                        Log.i("Paper", papers.get(j).select("h3[class=panelTitle]").select("a[class=togglePanel show]").text());
                        Log.i("Counter", j.toString());
                    }

                }







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
