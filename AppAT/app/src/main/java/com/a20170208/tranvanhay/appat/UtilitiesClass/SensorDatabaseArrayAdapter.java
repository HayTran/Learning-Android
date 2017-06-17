package com.a20170208.tranvanhay.appat.UtilitiesClass;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

/**
 * Created by Van Hay on 17-Jun-17.
 */

public class SensorDatabaseArrayAdapter extends ArrayAdapter {
    Context context;
    int layoutResource;
    ArrayList <DataPoint> dataPointArrayList;

    public SensorDatabaseArrayAdapter(Context context, int resource, ArrayList <DataPoint> dataPointArrayList) {
        super(context, resource, dataPointArrayList);
        this.context = context;
        this.layoutResource = resource;
        this.dataPointArrayList = dataPointArrayList;
    }


}
