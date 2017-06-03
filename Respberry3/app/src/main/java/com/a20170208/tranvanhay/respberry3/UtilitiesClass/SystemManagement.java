package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Van Hay on 03-Jun-17.
 */

public class SystemManagement {
    private static final String TAG = SystemManagement.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    HashMap <String,SensorNode> sensorNodeHashMap;
    HashMap <String,PowDevNode> powDevNodeHashMap;
    private boolean alcondMQ2, alcondMQ7, alcondTemperature, alcondHumidity, alcondMeanFlameValue, alcondLightIntensity;
    private int selectionNumber;

    public SystemManagement() {
        this.getAlertCondition();
    }

    private void getAlertCondition(){
        mData.child(FirebasePath.ALERT_CONDITION_CONFIG_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                selectionNumber = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    boolean value = Boolean.valueOf(dataSnapshot1.getValue().toString());
                    if (dataSnapshot1.getKey().equals("MQ2")) {
                        alcondMQ2 = value;
                    }   else if (dataSnapshot1.getKey().equals("MQ7")) {
                        alcondMQ7 = value;
                    }   else if (dataSnapshot1.getKey().equals("temperature")) {
                        alcondTemperature = value;
                    }   else if (dataSnapshot1.getKey().equals("humidity")) {
                        alcondHumidity = value;
                    }   else if (dataSnapshot1.getKey().equals("meanFlameValue")) {
                        alcondMeanFlameValue = value;
                    }   else if (dataSnapshot1.getKey().equals("lightIntensity")) {
                        alcondLightIntensity = value;
                    }
                        // Increase selectionNumber when boolean is true
                    if (value == true){
                        selectionNumber++;
                    }
                }
                Log.d(TAG,"Get value config for sensor with selectionNumber: " + selectionNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void checkSensorValue(){
        for (SensorNode sensorNode : sensorNodeHashMap.values()) {
            int count = 0;
            if (sensorNode.getTemperature() > sensorNode.getConfigTemperature()) {
                count++;
            }
            if (sensorNode.getHumidity() > sensorNode.getConfigHumidity()) {
                count++;
            }
            if (sensorNode.getLightIntensity() > sensorNode.getConfigLightIntensity()) {
                count++;
            }
            if (sensorNode.getMeanFlameValue() > sensorNode.getConfigMeanFlameValue()) {
                count++;
            }
            if (sensorNode.getMQ2() > sensorNode.getConfigMQ2()) {
                count++;
            }
            if (sensorNode.getMQ7() > sensorNode.getConfigMQ7()) {
                count++;
            }
            if (count >= selectionNumber) {
                Log.d(TAG,"Exceed selection number at " + sensorNode.getID());
            }
            Log.d(TAG,"Count = " + count);
        }
    }
    public void checkSystem(HashMap<String, SensorNode> sensorNodeHashMap, HashMap<String, PowDevNode> powDevNodeHashMap ){
        this.sensorNodeHashMap = sensorNodeHashMap;
        this.powDevNodeHashMap = powDevNodeHashMap;
        this.checkSensorValue();
        Log.d(TAG,"Checked System");
    }


}
