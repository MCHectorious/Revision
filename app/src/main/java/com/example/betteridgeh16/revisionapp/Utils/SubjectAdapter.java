package com.example.betteridgeh16.revisionapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.betteridgeh16.revisionapp.R;
import com.example.betteridgeh16.revisionapp.Utils.Subject;

import java.util.Random;

/**
 * Created by betteridgeh16 on 2/28/2017.
 */

public class SubjectAdapter extends ArrayAdapter<Subject> {
    Context context;
    int layoutResourceId;
    Subject data[] = null;

    public SubjectAdapter(Context context,  int layoutResourceId, Subject[] data) {
        super(context,  layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    static class SubjectHolder{
        TextView iconView;
        TextView subjectView,examboardView,dateView,qualificationView;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        View row = convertView;
        SubjectHolder holder = null;

        if(row==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SubjectHolder();
            holder.iconView = (TextView)row.findViewById(R.id.subjectIcon);
            holder.subjectView = (TextView)row.findViewById(R.id.subjectTextView);
            holder.examboardView = (TextView)row.findViewById(R.id.examBoardTextView);
            holder.dateView = (TextView)row.findViewById(R.id.importantDateTextView);
            holder.qualificationView = (TextView)row.findViewById(R.id.qualificationTypeTextView);

            row.setTag(holder);
        }else{
            holder = (SubjectHolder)row.getTag();
        }

        Subject subject = data[position];
        holder.iconView.setText(subject.icon);

        //Random random = new Random();
        String stringToHash = subject.subject + subject.examboard + subject.qualification;
        int r=19;
        int g=29;
        int b=31;

        for (int i = 0; i<stringToHash.length();i++){
            r=11*r + stringToHash.charAt(i);
            g=103*r + stringToHash.charAt(i);
            b=41*r + stringToHash.charAt(i);
            //Log.i("char",Integer.toString(stringToHash.charAt(i)));
        }

        r = Math.abs(r%200);
        g = Math.abs(g%200);
        b = Math.abs(b%200);

        //Log.i("r",Integer.toString(r));
        //Log.i("g",Integer.toString(g));
        //Log.i("b",Integer.toString(b));
        //r=200;
        //g=200;
        //b=200;


        holder.iconView.setBackgroundColor(Color.rgb(r,g,b));
        //holder.iconView.setImageBitmap(subject.icon);
        holder.subjectView.setText(subject.subject);
        holder.examboardView.setText(subject.examboard);
        holder.dateView.setText(subject.date);
        holder.qualificationView.setText(subject.qualification);

        return row;
    }
}
