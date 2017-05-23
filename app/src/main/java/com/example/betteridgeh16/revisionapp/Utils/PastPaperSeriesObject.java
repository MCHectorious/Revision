package com.example.betteridgeh16.revisionapp.Utils;

import java.util.Arrays;

/**
 * Created by betteridgeh16 on 5/23/2017.
 */

public class PastPaperSeriesObject {
    String seriesName;
    String[] pastPapersForThisSeries;

    public PastPaperSeriesObject(String seriesName, String[] pastPapersForThisSeries){
        this.pastPapersForThisSeries = pastPapersForThisSeries;
        this.seriesName = seriesName;
    }


    public String[] getPastPapersForThisSeries() {
        return pastPapersForThisSeries;
    }

    public String getSeriesName() {
        return seriesName;
    }
}
