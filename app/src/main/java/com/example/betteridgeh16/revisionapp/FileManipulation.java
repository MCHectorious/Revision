package com.example.betteridgeh16.revisionapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public static void writeToFile(String data,String file, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file+ ".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static void appendToFile(String data,String file, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file+ ".txt", Context.MODE_APPEND));
            outputStreamWriter.append("\n\r"+ data);
            Log.i("!!!!!!!!!!!!!!!!!", fileToString(file, context));
            //outputStreamWriter.write(fileToString(file,context) + "\r\n" + data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String fileToString(String file, Context context) {

        String result = "";

        try {
            InputStream inputStream = context.openFileInput(file + ".txt");

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
    public static List<String> fileToStringList(String file, Context context) {

        List<String> result = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput(file + ".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    result.add(bufferedReader.readLine());
                    Log.i("subject found",(receiveString==null)?"Error":receiveString);
                }

                inputStream.close();

            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return result;
    }

    public  static String[] fileToStringArray(String file,Context context){
        List<String> result = new ArrayList<>();
        String line;
        String[] data;

        try {
            /*InputStream inputStream = context.openFileInput(file + ".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    result.add(bufferedReader.readLine());
                    Log.i("found",(bufferedReader.readLine()==null)?"Error":receiveString);
                }

                inputStream.close();

            }*/
            BufferedReader abc = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<String>();

            while(( line = abc.readLine()) != null) {
                lines.add(line);
                System.out.println(line);
            }
            abc.close();

// If you want to convert to a String[]
            data = lines.toArray(new String[]{});
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        //String[] resultArray = new String[result.size()];

        //for (int i=0; i<resultArray.length;i++){
        //    resultArray[i] = result.get(i);
        //}


        return data;
    }

    public static void clearAll(Context context){
        FileManipulation.writeToFile("","courses",context);
        FileManipulation.writeToFile("","websites",context);
        FileManipulation.writeToFile("","examboards",context);
        FileManipulation.writeToFile("" ,"qualifications",context);
        FileManipulation.writeToFile("","importantdates",context);
    }
}
