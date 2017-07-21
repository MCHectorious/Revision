package com.example.betteridgeh16.revisionapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.betteridgeh16.revisionapp.Utils.FileManipulation;
import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.GraphicsManipulation;
import com.example.betteridgeh16.revisionapp.Utils.Subject;
import com.example.betteridgeh16.revisionapp.Utils.SubjectAdapter;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent = new Intent(Home.this, ExamBoards.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.add_white);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        String CoursesString = FileManipulation.fileToString(Home.this,"courses");
        Log.i("CoursesString",CoursesString);
        if(CoursesString.equals("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            TextView textView = new TextView(this);
            textView.setText("You currently have no courses."+ System.getProperty("line.separator") + "To add courses and continue click OKAY");
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
            ArrayList<Subject> subjectData = new ArrayList<>();
            Log.i(" ", FileManipulation.fileToString(Home.this, "courses"));
            String[] subject = FileManipulation.fileToStringArray(Home.this,"courses");
            String[] examboard = FileManipulation.fileToStringArray(Home.this,"examboards");
            String[] qualification = FileManipulation.fileToStringArray(Home.this,"qualifications");
            String[] importantDate = FileManipulation.fileToStringArray(Home.this, "importantdates");

            Log.i("subject size", Integer.toString(subject.length));
            for(int i = 0;i<subject.length;i++){
                Log.i("test",subject[i]);
                subjectData.add(new Subject(subject[i],subject[i],examboard[i],qualification[i],importantDate[i]));
            }
            SubjectAdapter adapter = new SubjectAdapter(Home.this,R.layout.subject_list_item,subjectData.toArray(new Subject[0]));
            ListView listView = (ListView) findViewById(R.id.HomeList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Home.this, Course.class);
                    intent.putExtra("Subject Index", position);
                    startActivity(intent);


                }

            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub

                    Log.v("long clicked","pos: " + pos);
                    AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);
                    alert.setTitle("Delete Course? - Coming Soon");
                    alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface d, int which) {
                            //onTouchListener should recognize which friend is being pressed on,
                            //similar to onItemClickListener

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Do Nothing
                        }
                    });

                    alert.create().show();

                    return true;
                }
            });


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
