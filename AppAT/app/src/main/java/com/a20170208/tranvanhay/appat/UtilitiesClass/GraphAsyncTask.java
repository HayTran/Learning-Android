package com.a20170208.tranvanhay.appat.UtilitiesClass;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


/**
 * Created by Van Hay on 17-Jun-17.
 */

public class GraphAsyncTask extends AsyncTask <Void, Integer, BarGraphSeries<DataPoint>>{
    public static  final int X_RESOLUTION = 40;
    private static final String TAG = GraphAsyncTask.class.getSimpleName();
    private ArrayList <Double> rawValueArrayList;
    private double minValue, maxValue;

        // Declare graphView, datasnapshot and convertedName variable in fragment
    private GraphView graphView;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private DataSnapshot dataSnapshot;
    private String convertedName;

    public GraphAsyncTask(GraphView graphView, AVLoadingIndicatorView avLoadingIndicatorView, DataSnapshot dataSnapshot, String convertedName) {
        this.graphView = graphView;
        this.avLoadingIndicatorView = avLoadingIndicatorView;
        this.dataSnapshot = dataSnapshot;
        this.convertedName = convertedName;
        rawValueArrayList = new ArrayList<>();
        maxValue = -1;
        minValue = 65536;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        graphView.setVisibility(View.GONE);
        avLoadingIndicatorView.smoothToShow();
    }

    @Override
    protected BarGraphSeries<DataPoint> doInBackground(Void... params) {
        for (DataSnapshot dataSnapshot1 : this.dataSnapshot.getChildren()){
            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                if (dataSnapshot2.getKey().equals(this.convertedName)){
                    this.addValueToRawValueArrayList(Double.valueOf(dataSnapshot2.getValue().toString()));
                }
            }
        }
        ArrayList <DataPoint> dataPointList = this.seperate();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(dataPointList.toArray(new DataPoint[dataPointList.size()]));
        return series;
    }

    @Override
    protected void onPostExecute(BarGraphSeries<DataPoint> series) {
        super.onPostExecute(series);
            // Animation
        avLoadingIndicatorView.smoothToHide();
        avLoadingIndicatorView.setVisibility(View.GONE);
        graphView.setVisibility(View.VISIBLE);
            // Add series
        graphView.removeAllSeries();
        graphView.addSeries(series);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(100);
        // Set number Vertical and horizontal label in the graphAsyncTask
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
    }

    /**
     *
     * @param addedValue
     */
    public void addValueToRawValueArrayList(Double addedValue){
            // avoid lightIntensity exceed
        if (addedValue < 30000) {
            this.rawValueArrayList.add(addedValue);
        }
        if (minValue > addedValue) {
            minValue = addedValue;
        }
        if (maxValue < addedValue && addedValue < 30000){
            maxValue = addedValue;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList <DataPoint> seperate(){
        Log.d(TAG,"#######################################" +convertedName
                + "\nMinValue: "+ minValue + "/ MaxValue: " + maxValue);
        ArrayList <DataPoint> dataPointList = new ArrayList<>();
        Log.d(TAG,"Size of array list: " + rawValueArrayList.size());
        int counterComparedElement = 0;
        if (maxValue == minValue) {
                // virtual border point
            minValue = 0;
            maxValue = 100;
        }
        double space =  (maxValue - minValue) / X_RESOLUTION;
        Log.d(TAG,"Space = " + space);
        dataPointList.add(new DataPoint(minValue-0.5,0.0));
        for (int i = 0; i < X_RESOLUTION; i++) {
                // Min and Max Value which used to compare added value
            double minComparedValue = (minValue + space*i);
            double maxComparedValue = (minValue + space*(i+1));
                // Count variable to count number of segment
            double count = 0;
            for (int j = 0; j < rawValueArrayList.size(); j ++) {
                if (rawValueArrayList.get(j) > minComparedValue
                        && rawValueArrayList.get(j) <= maxComparedValue) {
                    count++;
                    counterComparedElement ++;
                }
            }
            count = (count / rawValueArrayList.size())*100.0;
            dataPointList.add(new DataPoint(maxComparedValue,count));
//            Log.d(TAG,convertedName + " %%%% " +  maxComparedValue + " & count= " + count);
        }
        Log.d(TAG,"Size of counterComparedElement: " + counterComparedElement);
        Log.d(TAG,"dataPointArrayList size: " + dataPointList.size());
        return dataPointList;
    }


}
