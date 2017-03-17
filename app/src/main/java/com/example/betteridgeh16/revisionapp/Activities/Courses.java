package com.example.betteridgeh16.revisionapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;
import com.example.betteridgeh16.revisionapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Courses extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    String subject,website,examBoard,qualification, importantDate;
    String subjectWebsite;
    Integer timeoutlength = 50000; //TODO:Base this number on connection speed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);


        //String examBoard = getIntent().getStringExtra("ExamBoard");


        (new Subject()).execute();






    }
    private class Subject extends AsyncTask<Void, Void, Void> { //TODO:See about having just one async task with different parameters

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
                Document document = Jsoup.connect("http://www.aqa.org.uk/subjects").timeout(timeoutlength).get();
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
            Log.i("Number of subjects",Integer.toString(CourseList.size()));
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
                            subjectWebsite = element.attr("href");
                        }
                    }


                    (new ExactSubject()).execute();
                }

            });

            mProgressDialog.dismiss();



        }
    }
    private class ExactSubject extends AsyncTask<Void, Void, Void> {//TODO:Implement caching

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
            WebsiteList.add(e.attr("href"));

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(subjectWebsite).timeout(timeoutlength).get();
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

                    //startActivity(intent);
                    Snackbar.make(view,CourseList.toArray(new String[0])[position],Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();

                    subject = CourseList.toArray(new String[0])[position];
                    website = WebsiteList.toArray(new String[0])[position];
                    examBoard = "AQA";

                    //Log.i("Testing","Did get this far");
                    (new CourseInfo()).execute();

                    /*if(FileManipulation.fileToString("courses",Courses.this).equals("")){
                        FileManipulation.writeToFile(subject,"courses",Courses.this);
                        FileManipulation.writeToFile(website,"websites",Courses.this);
                        FileManipulation.writeToFile(examBoard,"examboard",Courses.this);
                    }else{
                        FileManipulation.appendToFile(subject,"courses",Courses.this);
                        FileManipulation.appendToFile(website,"websites",Courses.this);
                    }
                    intent.putExtra("subjectListUpdated", true);
                    startActivity(intent);
                    */

                }

            });

            mProgressDialog.dismiss();
            //TODO: use http://tika.apache.org/ to extract text from pdf or use http://www.rgagnon.com/javadetails/java-extract-text-from-a-pdf.html



        }
    }
    private class CourseInfo extends AsyncTask<Void, Void, Void> {

        //String[] courses;

        List<String> CourseList = new ArrayList<>();
        List<String> WebsiteList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Courses.this);
            mProgressDialog.setTitle("Information Additional information");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }



        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(website).timeout(timeoutlength).get();
                // Get the html document title

                /*Elements codesAndReferences = document.select("table[class=tableCodes]").select("tr");

                for (Element e:codesAndReferences){
                    if(e.child(0).text().equals("Qualification type")){
                        qualification  = (e.child(1).text()==null)? "Error":e.child(1).text();
                    }
                }
                */
                Elements menu = document.select("ol[class=menus").select("a[href]");
                qualification = (menu.isEmpty())? "Error":menu.last().text();




                Elements keyDates = document.select("ul[class=listEvents]").select("li").select("span[class=timestamp]");

                importantDate = (keyDates.isEmpty())? "Error":keyDates.first().text();

                //for (Element e:keyDates){
                //    importantDate  = (e.child(0).child(0).text()==null)? "Error":e.child(0).child(0).text();
                //    importantDate = e.child(0).child(0).text();
                //}


                //if(CourseList.size()==0){
                 //   Log.i("None Found","Yes");
                  //  CourseList.add("No courses were found. Sorry.");
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            Log.i("course",subject);
            Log.i("website",website);
            Log.i("examBoard",examBoard);
            Log.i("qualification",qualification );
            Log.i("important date",importantDate);

            //if(FileManipulation.fileToString("courses",Courses.this).equals("")){
            //    FileManipulation.writeToFile(subject,"courses",Courses.this);
            //    FileManipulation.writeToFile(website,"websites",Courses.this);
            //    FileManipulation.writeToFile(examBoard,"examboards",Courses.this);
            //    FileManipulation.writeToFile(qualification,"qualifications",Courses.this);
            //    FileManipulation.writeToFile(importantDate,"importantdates",Courses.this);
            //}else{







            //}
            Log.i("Courses", FileManipulation.fileToString(Courses.this,"courses"));
            Log.i("websites",FileManipulation.fileToString(Courses.this,"websites" ));
            Log.i("examboards",FileManipulation.fileToString(Courses.this, "examboards"));
            Log.i("qualifications",FileManipulation.fileToString(Courses.this, "qualifications" ));
            Log.i("importantdates",FileManipulation.fileToString(Courses.this,"importantdates"));


            FileManipulation.writeToFile(Courses.this,"courses",subject);
            FileManipulation.writeToFile(Courses.this,"websites",website);
            FileManipulation.writeToFile(Courses.this,"examboards",examBoard);
            FileManipulation.writeToFile( Courses.this,"qualifications",qualification);
            FileManipulation.writeToFile(Courses.this,"importantdates",importantDate);


            Log.i("Courses",FileManipulation.fileToString(Courses.this,"courses"));
            Log.i("websites",FileManipulation.fileToString(Courses.this,"websites" ));
            Log.i("examboards",FileManipulation.fileToString(Courses.this, "examboards"));
            Log.i("qualifications",FileManipulation.fileToString(Courses.this, "qualifications" ));
            Log.i("importantdates",FileManipulation.fileToString(Courses.this,"importantdates"));



            Intent intent = new Intent(Courses.this, Home.class);
            startActivity(intent);
            mProgressDialog.dismiss();



        }
    }
}
