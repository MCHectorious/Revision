package com.example.betteridgeh16.revisionapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.betteridgeh16.revisionapp.R;

public class ExamBoards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_boards);
        ListView listView = (ListView) findViewById(R.id.List2);
        final String[] examBoards = new String[] {"AQA","Other Examboards - Coming Soon"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, examBoards);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExamBoards.this, CoursesList.class);
                int itemPosition = position;
                intent.putExtra("ExamBoard", examBoards[position]);
                startActivity(intent);


            }

        });
    }
}
