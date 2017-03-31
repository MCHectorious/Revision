package com.example.betteridgeh16.revisionapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ImageWriter;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by betteridgeh16 on 3/30/2017.
 */

public class GraphicsManipulation {
    public static void deleteImage(Context context, String fileName){
        context.deleteFile(fileName + ".PNG");

    }

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
        canvas.drawText(text, 0, 50f, textPaint);
        FileOutputStream out = null;
        try {
            out = context.openFileOutput(text+".png",Context.MODE_APPEND);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Bitmap getIcon(Context context, String text){
        text = text.substring(0,1).toUpperCase();
        File file = new File(text+".png");
        if (!file.exists()){
            createImageFromChar(context, text);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;
    }
}
