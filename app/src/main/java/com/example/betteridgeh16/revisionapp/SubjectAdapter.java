package com.example.betteridgeh16.revisionapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView iconView;
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
            holder.iconView = (ImageView)row.findViewById(R.id.subjectIcon);
            holder.subjectView = (TextView)row.findViewById(R.id.subjectTextView);
            holder.examboardView = (TextView)row.findViewById(R.id.examBoardTextView);
            holder.dateView = (TextView)row.findViewById(R.id.importantDateTextView);
            holder.qualificationView = (TextView)row.findViewById(R.id.qualificationTypeTextView);

            row.setTag(holder);
        }else{
            holder = (SubjectHolder)row.getTag();
        }

        Subject subject = data[position];
        holder.iconView.setImageResource(subject.icon);
        holder.subjectView.setText(subject.subject);
        holder.examboardView.setText(subject.examboard);
        holder.dateView.setText(subject.date);
        holder.qualificationView.setText(subject.qualification);

        return row;
    }
}
