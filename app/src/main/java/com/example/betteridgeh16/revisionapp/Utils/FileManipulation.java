package com.example.betteridgeh16.revisionapp.Utils;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by betteridgeh16 on 2/23/2017.
 */

public class FileManipulation{
    public static void createFile(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName + ".txt");
    }

    public  static void deleteFile(Context context, String fileName){
        context.deleteFile(fileName + ".txt");
    }

    public static void writeToFile(Context context, String fileName, String data){
        try{
            OutputStream outputStream =context.openFileOutput(fileName+ ".txt", Context.MODE_APPEND);
            PrintWriter writer = new PrintWriter(outputStream);
            //FileOutputStream outputStream =
            writer.println(data);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void appendToFile(Context context, String fileName, String data){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName+ ".txt", Context.MODE_APPEND);
            String endOfLine = System.getProperty("line.separator");
            outputStream.write((data+endOfLine).getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String[] fileToStringArray(Context context, String filename){
        String line;
        ArrayList<String> result = new ArrayList<>();

        try {

            FileInputStream fileIn = context.openFileInput(filename + ".txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileIn));
                   // new BufferedReader(new FileReader(filename+ ".txt"));


            while ((line = bufferedReader.readLine()) != null){
                result.add(line);
            }

            bufferedReader.close();

            }catch (IOException e){
            Log.e("IOError", e.getMessage());

        }
        Log.i("Array", result.toString());
        return result.toArray(new String[0]);
    }

    public static String fileToString(Context context, String fileName){

        String s="";
        try{
            FileInputStream fileIn= context.openFileInput(fileName + ".txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
        }catch(IOException e){
            Log.i("IOError",e.getMessage());
        }


        return s; //TODO: http://www.journaldev.com/9383/android-internal-storage-example-tutorial
    }

    public static String getRandomLineFromTextFile(Context context, String fileName){
        Boolean foundLine = false;
        String output="";
        try{
            FileInputStream fileIn= context.openFileInput(fileName + ".txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[50];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0 && !foundLine) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                if ( (int)(Math.random()*10) == 1){
                    output =readstring;//TODO: Use characters to increase speed and randomness
                    foundLine = true;
                }

            }
            InputRead.close();
        }catch(IOException e){
            Log.i("IOError",e.getMessage());
        }

        return output;
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