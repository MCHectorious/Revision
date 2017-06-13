package com.example.betteridgeh16.revisionapp.Utils;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by betteridgeh16 on 3/30/2017.
 */

public class StringManipulation {
    public static String trimStringToCourse(String text){
        String[] prefixesToRemove = {"A-level ", "GCSE", "GCSE ","AS","AS "};
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

    public static Integer numberofCharacter(String message, Character character){
        Integer counter = 0;
        for (Integer i = 0; i<message.length();i++){
            if ((message.charAt(i))==character){
                counter++;
            }
        }

        return counter;
    }

    public static Integer[] arrayOfPositionsOfCharacter(String message, Character character){
        ArrayList<Integer> result = new ArrayList<>();


        for (Integer i = 0; i<message.length();i++){
            if ((message.charAt(i))==character){
                result.add(i);
            }
        }

        return result.toArray(new Integer[0]);
    }

    public static String AsterixiseBetweenBound(String string, Integer beginIndex, Integer endIndex){
        String axterixes = "";
        for (Integer i = beginIndex+1; i<endIndex;i++){
            axterixes += "_";
        }

        return string.substring(0,beginIndex+1)+axterixes+string.substring(endIndex);
    }

}
