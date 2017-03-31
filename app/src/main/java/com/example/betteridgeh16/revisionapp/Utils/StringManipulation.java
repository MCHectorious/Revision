package com.example.betteridgeh16.revisionapp.Utils;

import android.util.Log;

/**
 * Created by betteridgeh16 on 3/30/2017.
 */

public class StringManipulation {
    public static String trimStringToCourse(String text){
        String output = "";
        String[] prefixesToRemove = {"A-level ", "GCSE"};
        String[] phrasesToRemove = {"New ", "New"};
        for (String s:prefixesToRemove){
            if(text.startsWith(s)){
                text = text.substring(s.length());
            }
        }
        for (String s:phrasesToRemove){
            if(text.contains(s)){
                Log.i("Phrase to Remove", "True");
                text = text.substring(0, text.indexOf(s)) + text.substring(text.indexOf(s)+s.length());
            }
        }

        if(text.substring(text.lastIndexOf('(')+1,text.lastIndexOf(')')-1).matches("(Draft )*(0|1|2|3|4|5|6|7|8|9)+")){
            try{
                text = text.substring(0,text.lastIndexOf('(')-1).concat(text.substring(text.lastIndexOf(')')+1));
            }catch (Exception e){
                text = text.substring(0,text.lastIndexOf('(')-1);
            }

        }

        return text;
    }
}
