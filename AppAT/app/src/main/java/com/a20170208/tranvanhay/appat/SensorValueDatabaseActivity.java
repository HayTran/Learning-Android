package com.a20170208.tranvanhay.appat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.SensorDatabaseFragment.SensorDatabaseFragmentAdapter;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.Graph;
import com.a20170208.tranvanhay.appat.UtilitiesClass.SensorNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class SensorValueDatabaseActivity extends AppCompatActivity {
    private static final String TAG = SensorValueDatabaseActivity.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    TextView textViewID, textViewZone;
    SensorNode sensorNode;
    GraphView graphView;

    FragmentManager fm;
    SensorDatabaseFragmentAdapter fa;
    TabLayout tabLayout;
    ViewPager viewPager;
    double maxTemperature, minTemperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_database);
//        addCotrols();
//        init();
//        addEvents();
        addCotrols1();
        init1();
    }

    private void init1() {
            // Back icon
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sensor value database");
            // Get value
        Intent intent = getIntent();
        sensorNode = (SensorNode) intent.getSerializableExtra("SensorNodeObject");
        textViewID.setText(sensorNode.getID());
        textViewZone.setText(sensorNode.getZone()+"");
            // Set up fragment
        fm = getSupportFragmentManager();
        fa = new SensorDatabaseFragmentAdapter(fm);
        viewPager.setAdapter(fa);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void addCotrols1() {
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        textViewID = (TextView)findViewById(R.id.textViewID);
        textViewZone = (TextView)findViewById(R.id.textViewZone);
    }

    private void addEvents() {
        Query query0 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("temperature").limitToFirst(1);
        Query query1 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("temperature").limitToLast(1);
        Query query01 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ2");
        Query query2 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("humidity").limitToFirst(1);
        Query query3 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("humidity").limitToLast(1);
        Query query4 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("meanFlameValue").limitToFirst(1);
        Query query5 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("meanFlameValue").limitToLast(1);
        Query query6 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("lightIntensity").limitToFirst(1);
        Query query7 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("lightIntensity").limitToLast(1);
        Query query8 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ2").limitToFirst(1);
        Query query9 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ2").limitToLast(1);
        Query query10 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ7").limitToFirst(1);
        Query query11 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ7").limitToLast(1);

            // Temperature
        query0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"query0");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        if (dataSnapshot2.getKey().equals("temperature")){
                            minTemperature = Double.valueOf(dataSnapshot2.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"query1");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        if (dataSnapshot2.getKey().equals("temperature")){
                            maxTemperature = Double.valueOf(dataSnapshot2.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        query01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"query01");
                Graph graph = new Graph();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        if (dataSnapshot2.getKey().equals("MQ2")){
                            graph.addValueToRawValueArrayList(Double.valueOf(dataSnapshot2.getValue().toString()));
                        }
                    }
                }
                ArrayList <DataPoint> dataPointList = graph.seperate();
                BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(dataPointList.toArray(new DataPoint[dataPointList.size()]));
                graphView.addSeries(series);
                for (int i = 0; i < dataPointList.size(); i ++) {
                    Log.d(TAG,"X: " + dataPointList.get(i).getX());
                    Log.d(TAG,"Y: " + dataPointList.get(i).getY());
                }
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                    }
                });
                graphView.getViewport().setScalable(true);
                graphView.getViewport().setYAxisBoundsManual(true);
                graphView.getViewport().setMaxY(100);
                    // Set number Vertical and horizontal label in the graph
                graphView.getGridLabelRenderer().setNumVerticalLabels(10);
                graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
                graphView.setTitle("Temperature");
                graphView.setTitleColor(Color.RED);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init() {
            // Back icon
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sensor value database");
            // Get value
        Intent intent = getIntent();
        sensorNode = (SensorNode) intent.getSerializableExtra("SensorNodeObject");
        textViewID.setText(sensorNode.getID());
        textViewZone.setText(sensorNode.getZone()+"");

    }

    private void addCotrols(){
        textViewID = (TextView)findViewById(R.id.textViewID);
        textViewZone = (TextView)findViewById(R.id.textViewZone);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
