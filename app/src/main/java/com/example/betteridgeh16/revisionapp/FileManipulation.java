package com.example.betteridgeh16.revisionapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by betteridgeh16 on 2/23/2017.
 */

public class FileManipulation {
    //public static void createFile(){
    //    File file = new File("courses")
    //}
    public static void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("courses.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(Context context) {

        String result = "";

        try {
            InputStream inputStream = context.openFileInput("courses.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return result;
    }
    public static List<String> getSubjectsFromFile(Context context) {

        List<String> subjectList = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput("courses.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                //List<String> subjectList = new ArrayList<>();
                //List<String> websiteList = new ArrayList<>();


                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    subjectList.add(receiveString.substring(0,receiveString.indexOf('\t')));
                   // websiteList.add(receiveString.substring(receiveString.indexOf("\t")));
                }

               /* result = new String [subjectList.size()][2];
                for (int i = 1;i<=subjectList.size();i++){
                    result[i][1] = subjectList.toArray(new String[0])[i];
                    result[i][2] = websiteList.toArray(new String[0])[i];
                }
                */

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return subjectList;
    }
}
