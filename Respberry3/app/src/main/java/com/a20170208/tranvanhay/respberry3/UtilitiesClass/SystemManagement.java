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
    HashMap <String, Integer > conditionHashMap = new HashMap<>();
    private int selectionNumber;

    public SystemManagement() {
        this.getAlertCondition();
    }

    private void getAlertCondition(){
        mData.child(FirebasePath.CONTROLLER_CONDITION_CONFIG_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    conditionHashMap.clear();
                    selectionNumber = 0;
                    int value = Integer.valueOf(dataSnapshot1.getValue().toString());
                    if (dataSnapshot1.getKey().equals("MQ2")) {
                        conditionHashMap.put("MQ2",value);
                    }   else if (dataSnapshot1.getKey().equals("MQ7")) {
                        conditionHashMap.put("MQ7",value);
                    }   else if (dataSnapshot1.getKey().equals("temperature")) {
                        conditionHashMap.put("temperature",value);
                    }   else if (dataSnapshot1.getKey().equals("humidity")) {
                        conditionHashMap.put("humidity",value);
                    }   else if (dataSnapshot1.getKey().equals("meanFlameValue")) {
                        conditionHashMap.put("meanFlameValue",value);
                    }   else if (dataSnapshot1.getKey().equals("lightIntensity")) {
                        conditionHashMap.put("lightIntensity",value);
                    }
                    if (value > 0) {
                        selectionNumber++;
                    }
                }
                Log.d(TAG,"Get value config for sensor with selectionNumber: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void check(){
//        if (check(100)){
//            // 100 corresponding to alert through Internet or GSM
//            Log.d(TAG,"OK Alert");
//        } else if (checkCondition(200)){
//            // 200 corresponding to implement controller PowDev
//            Log.d(TAG,"OK Implement Controller");
//        }
        checkAllSensorNode();
    }
    private void checkAllSensorNode(){
        for (SensorNode sensorNode : sensorNodeHashMap.values()) {
            if (checkEachSensorNode(sensorNode,100)){
                Log.d(TAG,"OK Alert");
            }
        }
    }
    private boolean checkEachSensorNode(SensorNode sensorNode, int typeOperation){
        int count = 0;
        if (sensorNode.getTemperature() >= sensorNode.getConfigTemperature() && conditionHashMap.get("temperature") == typeOperation){
            count++;
        }
        if (sensorNode.getHumidity() >= sensorNode.getConfigHumidity() && conditionHashMap.get("humidity") == typeOperation){
            count++;
        }
        if (sensorNode.getMeanFlameValue() >= sensorNode.getConfigMeanFlameValue() && conditionHashMap.get("meanFlameValue") == typeOperation){
            count++;
        }
        if (sensorNode.getLightIntensity() >= sensorNode.getConfigLightIntensity() && conditionHashMap.get("lightIntensity") == typeOperation){
            count++;
        }
        if (sensorNode.getMQ2() >= sensorNode.getConfigMQ2() && conditionHashMap.get("MQ2") == typeOperation){
            count++;
        }
        if (sensorNode.getMQ7() >= sensorNode.getConfigMQ7() && conditionHashMap.get("MQ7") == typeOperation){
            count++;
        }
        if (count == selectionNumber && count > 0){
            return true;
        } else {
            return false;
        }
    }

    public void checkSystem(HashMap<String, SensorNode> sensorNodeHashMap, HashMap<String, PowDevNode> powDevNodeHashMap ){
        this.sensorNodeHashMap = sensorNodeHashMap;
        this.powDevNodeHashMap = powDevNodeHashMap;
        this.check();
        Log.d(TAG,"Checked System");
    }


}
