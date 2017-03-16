package com.example.betteridgeh16.revisionapp;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class FileManipulation{
    public static void createFile(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);
    }

    public  static void deleteFile(Context context, String fileName){
        context.deleteFile(fileName);
    }

    public static void writeToFile(Context context, String fileName, String data){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void appendToFile(Context context, String fileName, String data){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String[] fileToArray(Context context, String filename){
        String line;
        ArrayList<String> result = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));


            while ((line = bufferedReader.readLine()) != null){
                result.add(line);
            }

            bufferedReader.close();

            }catch (IOException e){
            Log.e("Error", e.getMessage());

        }

        return result.toArray(new String[0]);
    }

    public static String fileToString(Context context, String file){

        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            BufferedReader reader = new BufferedReader(new FileReader (file));
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            reader.close();

        }catch(IOException e){
            Log.e("Error",e.getMessage());

        }
        return stringBuilder.toString();
    }

   /* public static Boolean isEmpty(Context context, String filename){
        Boolean result;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));


            bufferedReader.readLine()) != null){

            }

            bufferedReader.close();

        }catch (IOException e){
            Log.e("Error", e.getMessage());

        }
    }*/

}












































/*
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
            outputStreamWriter.append("\t"+ data);
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
            Log.e(" ", "File not found: " + e.toString());
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
        List<String> lines = new ArrayList<String>();


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

            }
            BufferedReader abc = new BufferedReader(new FileReader(file));


            while(( line = abc.readLine()) != null) {
                lines.add(line);
                Log.i("line: ",line);
            }
            abc.close();

// If you want to convert to a String[]

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

        data = lines.toArray(new String[]{});
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
*/