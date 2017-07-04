package com.a20170208.tranvanhay.appat.SensorDatabaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.GraphAsyncTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.wang.avi.AVLoadingIndicatorView;

import static android.content.ContentValues.TAG;

/**
 * Created by Van Hay on 17-Jun-17.
 */

public class GraphFragment extends Fragment {
    private String name;
    TextView textView;
    GraphView graphView;
    AVLoadingIndicatorView avLoadingIndicatorView;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public GraphFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor_database,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        init();
        addEvents();

    }

    private void addEvents() {

    }

    private void init() {
        Query query01 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child("SensorNode0")
                .orderByChild(this.getConvertName());
        query01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"query01");
                GraphAsyncTask graphAsyncTask = new GraphAsyncTask(graphView, avLoadingIndicatorView,dataSnapshot,getConvertName());
                graphAsyncTask.execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addControls(View view) {
        graphView = (GraphView)view.findViewById(R.id.graph);
        avLoadingIndicatorView = (AVLoadingIndicatorView)view.findViewById(R.id.avLoadingIndicatorView);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getConvertName(){
        String convertedName = null;
        switch (this.name){
            case "Temperature":
                convertedName = "temperature";
                break;
            case "Humidity":
                convertedName = "humidity";
                break;
            case "Flame":
                convertedName = "meanFlameValue";
                break;
            case "Light Intensity":
                convertedName = "lightIntensity";
                break;
            case "MQ2":
                convertedName = "MQ2";
                break;
            case "MQ7":
                convertedName = "MQ7";
                break;
        }
        return convertedName;
    }


}
