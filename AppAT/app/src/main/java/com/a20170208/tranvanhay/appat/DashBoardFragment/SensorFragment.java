package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.SensorNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Van Hay on 30-May-17.
 */

public class SensorFragment extends Fragment {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = SensorFragment.class.getSimpleName();
    SensorArrayAdapter arrayAdapter;
    ListView listView;
    ArrayList <SensorNode> sensorNodeArrayList;

    public SensorFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor,null);
        Log.d(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
        init();
        addEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
            // Delete event listener when fragment stop
//        mData.child(FirebasePath.SENSOR_CURRENT_VALUE_PATH).removeEventListener(detailsSensorNodeListener);
//        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH).removeEventListener(configSensorNodeListener);
    }

    private void addControl(View view ) {
        listView = (ListView)view.findViewById(R.id.listView);
    }

    private void init() {
        sensorNodeArrayList = new ArrayList<>();
        arrayAdapter = new SensorArrayAdapter(getContext(),R.layout.sensor_row, sensorNodeArrayList);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    private void addEvent() {
        mData.child(FirebasePath.SENSOR_LIST_PATH).addListenerForSingleValueEvent(listSensorNodeListener);
    }
    private ValueEventListener listSensorNodeListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                SensorNode sensorNode = new SensorNode();
                sensorNode.setID(dataSnapshot1.getKey());
                sensorNodeArrayList.add(sensorNode);
                    // Wait until get all list of node sensor
                sensorNodeArrayList.add(new SensorNode("9f:0k","NodeSensorxxx",1,2,3,4,5,9,10,"11:32:00"));
                mData.child(FirebasePath.SENSOR_CURRENT_VALUE_PATH).addValueEventListener(detailsSensorNodeListener);
                mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH).addValueEventListener(configSensorNodeListener);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener detailsSensorNodeListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                for (SensorNode sensorNode : sensorNodeArrayList) {
                    String ID = sensorNode.getID();
                    if (dataSnapshot1.getKey().equals(ID)) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            if (dataSnapshot2.getKey().equals("MACAddr")){
                                sensorNode.setMACAddr(dataSnapshot2.getValue().toString());
                            }   else if (dataSnapshot2.getKey().equals("zone")){
                                sensorNode.setZone(Integer.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("temperature")){
                                sensorNode.setTemperature(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("humidity")){
                                sensorNode.setHumidity(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("meanFlameValue")){
                                sensorNode.setMeanFlameValue(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("lightIntensity")){
                                sensorNode.setLightIntensity(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("MQ2")){
                                sensorNode.setMQ2(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("MQ7")){
                                sensorNode.setMQ7(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("strengthWifi")){
                                sensorNode.setStrengthWifi(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("timeSend")){
                                sensorNode.setTimeSend(dataSnapshot2.getValue().toString());
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
    private ValueEventListener configSensorNodeListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                for (SensorNode sensorNode : sensorNodeArrayList) {
                    String ID = sensorNode.getID();
                    if (dataSnapshot1.getKey().equals(ID)){
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            if (dataSnapshot2.getKey().equals("temperature")){
                                sensorNode.setConfigTemperature(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }    else if (dataSnapshot2.getKey().equals("humidity")){
                                sensorNode.setConfigHumidity(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("meanFlameValue")){
                                sensorNode.setConfigMeanFlameValue(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("lightIntensity")){
                                sensorNode.setConfigLightIntensity(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("MQ2")){
                                sensorNode.setConfigMQ2(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("MQ7")){
                                sensorNode.setConfigMQ7(Double.valueOf(dataSnapshot2.getValue().toString()));
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
