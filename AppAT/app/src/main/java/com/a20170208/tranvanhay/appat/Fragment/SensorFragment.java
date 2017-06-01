package com.a20170208.tranvanhay.appat.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a20170208.tranvanhay.appat.R;
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
    ArrayList <NodeSensor> nodeSensorArrayList;

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
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
            // Delete event listener when fragment stop
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").removeEventListener(detailsNodeSensorListener);
    }

    private void addControl(View view ) {
        listView = (ListView)view.findViewById(R.id.listView);
    }

    private void init() {
        nodeSensorArrayList = new ArrayList<>();
        arrayAdapter = new SensorArrayAdapter(getContext(),R.layout.sensor_row_adapter,nodeSensorArrayList);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    private void addEvent() {
        mData.child("SocketServer").child("NodeList").child("NodeSensor").addListenerForSingleValueEvent(listNodeSensorListener);
    }
    private ValueEventListener listNodeSensorListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                NodeSensor nodeSensor = new NodeSensor();
                nodeSensor.setID(dataSnapshot1.getKey());
                nodeSensorArrayList.add(nodeSensor);
                    // Wait until get all list of node sensor
                nodeSensorArrayList.add(new NodeSensor("9f:0k","NodeSensorxxx",1,2,3,4,5,6,7,8,9,10,"11:32:00"));
                mData.child("SocketServer").child("NodeDetails").child("NodeSensor").addValueEventListener(detailsNodeSensorListener);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener detailsNodeSensorListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                for (NodeSensor nodeSensor : nodeSensorArrayList) {
                    String ID = nodeSensor.getID();
                    if (dataSnapshot1.getKey().equals(ID)) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            if (dataSnapshot2.getKey().equals("MACAddr")){
                                nodeSensor.setMACAddr(dataSnapshot2.getValue().toString());
                            } else if (dataSnapshot2.getKey().equals("temperature")){
                                nodeSensor.setTemperature(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("humidity")){
                                nodeSensor.setHumidity(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("flameValue0")){
                                nodeSensor.setFlameValue0(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("flameValue1")){
                                nodeSensor.setFlameValue1(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("flameValue2")){
                                nodeSensor.setFlameValue2(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("flameValue3")){
                                nodeSensor.setFlameValue3(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("lightIntensity")){
                                nodeSensor.setLightIntensity(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("MQ2")){
                                nodeSensor.setMQ2(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("MQ7")){
                                nodeSensor.setMQ7(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("strengthWifi")){
                                nodeSensor.setStrengthWifi(Double.valueOf(dataSnapshot2.getValue().toString()));
                            } else if (dataSnapshot2.getKey().equals("timeSend")){
                                nodeSensor.setTimeSend(dataSnapshot2.getValue().toString());
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
