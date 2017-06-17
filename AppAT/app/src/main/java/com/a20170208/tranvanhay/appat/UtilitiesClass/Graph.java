package com.a20170208.tranvanhay.appat.UtilitiesClass;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;


/**
 * Created by Van Hay on 17-Jun-17.
 */

public class Graph {
    public static final int X_RESOLUTION = 40;
    private static final String TAG = Graph.class.getSimpleName();
    private ArrayList <Double> rawValueArrayList;
    private double minValue, maxValue;

    public Graph() {
        rawValueArrayList = new ArrayList<>();
        maxValue = -1;
        minValue = 65536;
    }

    public void addValueToRawValueArrayList(Double addedValue){
        this.rawValueArrayList.add(addedValue);
        if (minValue > addedValue) {
            minValue = addedValue;
        }
        if (maxValue < addedValue){
            maxValue = addedValue;
        }
        Log.d(TAG,"MinValue: "+ minValue + "/ MaxValue: " + maxValue);
    }

    public void deleteRawValueArrayList(){
        this.rawValueArrayList.clear();
    }

    public int getSizeRawValueArrayList(){
        if (rawValueArrayList != null) {
            return rawValueArrayList.size();
        }   else {
            return -1;
        }
    }

    public ArrayList<Double> getRawValueArrayList() {
        return rawValueArrayList;
    }

    public void setRawValueArrayList(ArrayList<Double> rawValueArrayList) {
        this.rawValueArrayList = rawValueArrayList;
    }

    public ArrayList <DataPoint> seperate(){
        ArrayList <DataPoint> dataPointList = new ArrayList<>();
        Log.d(TAG,"Size of array list: " + rawValueArrayList.size());
        int counterComparedElement = 0;
        double space =  (maxValue - minValue) / X_RESOLUTION;
        for (int i = 0; i < X_RESOLUTION; i++) {
                // Min and Max Value which used to compare added value
            double minComparedValue = (minValue + space*i);
            double maxComparedValue = (minValue + space*(i+1));
                // Count variable to count number of segment
            double count = 0;
            for (int j = 0; j < rawValueArrayList.size(); j ++) {
                if (rawValueArrayList.get(j) > minComparedValue
                        && rawValueArrayList.get(j) < maxComparedValue) {
                    count++;
                    counterComparedElement ++;
                }
            }
            count = (count / rawValueArrayList.size())*100;
            dataPointList.add(new DataPoint(maxComparedValue,count));
        }
        Log.d(TAG,"Size of counterComparedElement: " + counterComparedElement);
        Log.d(TAG,"dataPointArrayList size: " + dataPointList.size());
        return dataPointList;
    }
}
