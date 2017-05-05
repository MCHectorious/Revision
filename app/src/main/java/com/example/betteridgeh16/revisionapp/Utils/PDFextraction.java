package com.example.betteridgeh16.revisionapp.Utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by betteridgeh16 on 4/20/2017.
 */
public class PDFextraction {
    public static void downloadPDF(String url, String subject, Context context){
        try{
            URL u = new URL(url);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            //File rootDataDir = context.getFilesDir();
            FileOutputStream f = new FileOutputStream(new File(context.getFilesDir(),subject+"Specification.pdf"));



            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ( (len1 = in.read(buffer)) > 0 ) {
                f.write(buffer,0, len1);
            }
            f.close();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void extractTextFromPDF(String subject, Context context){
        if (!new File(subject+"Specification.txt").exists()){
            //if(!new File(context.getFilesDir(),subject+"Specification.pdf").exists()){downloadPDF();
            String parsedText = null;
            PDDocument document = null;
            File file = new File(context.getFilesDir(),subject+"Specification.pdf");
            if(!file.exists()){
                Log.i("File", "does not exist");
            }else{
                Log.i("File", "does exist");
            }
            try {
                document = PDDocument.load(file);
            } catch(IOException e) {
                e.printStackTrace();
            }

            try {
                PDFBoxResourceLoader.init(context);
                PDFTextStripper pdfStripper = new PDFTextStripper();
                pdfStripper.setStartPage(0);
                //pdfStripper.setEndPage(10);
                parsedText = pdfStripper.getText(document);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (document != null) document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            FileManipulation.createFile(context, subject+"Specification");
            FileManipulation.writeToFile(context, subject+"Specification",parsedText);

            Log.i("Parsed Text", parsedText);


            /*if(new File(context.getFilesDir(), subject+"Specification.txt").exists()){
                Log.i("Text File", "Found after creating it");
            }else {
                Log.i("Text File", "Not found after creating it?");
            }*/
        }

    }


}



    /*public static void extractTextFromDownloadedPDF(String subject, Context context){
        try {
            String parsedText="";
            //String filePath = context.getFilesDir()+"/"+subject+"Specification.pdf";
            String filePath = subject+"Specification.pdf";
            String absoluteFilePath = context.getFilesDir().getAbsolutePath()+"/"+filePath;
            File file = new File(context.getFilesDir(),filePath);
            if(!file.exists()){
                Log.i("File", "does not exist");
            }else{
                Log.i("File", "does exist");
            }
            PdfReader reader = new PdfReader(absoluteFilePath);
            int n = reader.getNumberOfPages();
            for (int i = 0; i <n ; i++) {
                parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n";
            }
            Log.i("Extracted Text", parsedText);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractTextFromOnlinePDF(String url, Context context){
        try {
            String parsedText="";

            Log.i("Website", url);
            PdfReader reader = new PdfReader(new URL(url));
            int n = reader.getNumberOfPages();
            for (int i = 0; i <n ; i++) {
                parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n";
            }
            Log.i("Extracted Text", parsedText);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}/*
import com.aspose.pdf.Document;
import com.aspose.pdf.MemoryCleaner;
import com.aspose.pdf.TextAbsorber;


    // Open document
    Document pdfDocument = new Document("input.pdf");
    // Create TextAbsorber object to extract text
    TextAbsorber textAbsorber = new TextAbsorber();
    // Accept the absorber for all the pages
    pdfDocument.getPages().accept(textAbsorber);
    // Get the extracted text
    String extractedText = textAbsorber.getText();
    // Create a writer and open the file
    java.io.FileWriter writer = new java.io.FileWriter(new java.io.File("Extracted_text.txt"));
    writer.write(extractedText);
    // Write a line of text to the file
    // tw.WriteLine(extractedText);
    // Close the stream
    writer.close();
}
*/