package com.example.betteridgeh16.revisionapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by betteridgeh16 on 3/30/2017.
 */

public class GraphicsManipulation {

    public static void createImageFromChar(Context context, String text){
        final Paint textPaint = new Paint() {
            {
                setColor(Color.WHITE);
                setTextAlign(Paint.Align.LEFT);
                setTextSize(20f);
                setAntiAlias(true);
            }};
        final Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        final Bitmap bmp = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.RGB_565); //use ARGB_8888 for better quality
        final Canvas canvas = new Canvas(bmp);
        canvas.drawText(text, 0, 20f, textPaint);
        try{
            OutputStream stream = context.openFileOutput(text, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 85, stream);
            bmp.recycle();
            stream.close();
        }catch (IOException e){
            Log.e("IOError", e.getMessage());
        }


    }

    public static File getIcon(Context context, String text){
        File file = new File(text+".png");
        if (!file.exists()){
            createImageFromChar(context, text);
        }
        return file;
    }
}
