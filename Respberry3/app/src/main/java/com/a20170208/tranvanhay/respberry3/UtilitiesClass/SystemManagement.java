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
    HashMap <String, Integer > conditionHashMap;
    HashMap <String, Boolean > alertTypeHashMap;
    String MACAddrGSMNode;
    private int selectionNumber;

    public SystemManagement() {
        this.getControllerConfig();
        this.conditionHashMap = new HashMap<>();
        this.alertTypeHashMap = new HashMap<>();
    }

    private void getControllerConfig(){
        mData.child(FirebasePath.CONTROLLER_CONDITION_CONFIG_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    // Clear hashmap before put new value into hashmap
                conditionHashMap.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
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
                Log.d(TAG,"Get value config for sensor with selectionNumber: \n" +
                        "with hashmap's size: " +conditionHashMap.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    // Clear hashmap before put new value into hashmap
                alertTypeHashMap.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    boolean value = Boolean.valueOf(dataSnapshot1.getValue().toString());
                    if (dataSnapshot1.getKey().equals("SMSAlert")) {
                        alertTypeHashMap.put("SMSAlert",value);
                    }   else if (dataSnapshot1.getKey().equals("CallAlert")) {
                        alertTypeHashMap.put("CallAlert",value);
                    }   else if (dataSnapshot1.getKey().equals("InternetAlert")) {
                        alertTypeHashMap.put("InternetAlert",value);
                    }
                }
                Log.d(TAG,"Gotten alert type config with hashmap's size: " + alertTypeHashMap.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(FirebasePath.CONTROLLER_GSM_NODE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MACAddrGSMNode = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
        // Check all sensor node in the system
    private void checkAllSensorNode(){
        for (SensorNode sensorNode : sensorNodeHashMap.values()) {
                // Check alert
            if (checkEachSensorNode(sensorNode,100)){
                alert(sensorNode,true);
            } else {
                alert(sensorNode,false);
            }
                // Check control powdev
            if (checkEachSensorNode(sensorNode,200)){
                controlPowDev(sensorNode,true);
            } else {
                controlPowDev(sensorNode,false);
            }
        }
    }
        // Check each sensor node in the system
    private boolean checkEachSensorNode(SensorNode sensorNode, int typeOperation){
        int count = 0;
        if (sensorNode.getTemperature() >= sensorNode.getConfigTemperature() &&
                conditionHashMap.get("temperature") == typeOperation){
            count++;
        }
        if (sensorNode.getHumidity() >= sensorNode.getConfigHumidity() &&
                conditionHashMap.get("humidity") == typeOperation){
            count++;
        }
        if (sensorNode.getMeanFlameValue() >= sensorNode.getConfigMeanFlameValue() &&
                conditionHashMap.get("meanFlameValue") == typeOperation){
            count++;
        }
        if (sensorNode.getLightIntensity() >= sensorNode.getConfigLightIntensity() &&
                conditionHashMap.get("lightIntensity") == typeOperation){
            count++;
        }
        if (sensorNode.getMQ2() >= sensorNode.getConfigMQ2() &&
                conditionHashMap.get("MQ2") == typeOperation){
            count++;
        }
        if (sensorNode.getMQ7() >= sensorNode.getConfigMQ7() &&
                conditionHashMap.get("MQ7") == typeOperation){
            count++;
        }
        if (count == selectionNumber && count > 0){
            return true;
        } else {
            return false;
        }
    }
        // Control PowDev when sensor node exceed configured value
    private void controlPowDev(SensorNode sensorNode, boolean isActive ){
        int zone = sensorNode.getZone();
        if (isActive) {
            for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
                if (powDevNode.getZone() == zone) {
                    powDevNode.setDev0(0);
                    powDevNode.setDev1(0);
                    powDevNode.setBuzzer(0);
                }
            }
        } else {
            for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
                if (powDevNode.getZone() == zone) {
                    powDevNode.setDev0(1);
                    powDevNode.setDev1(1);
                    powDevNode.setBuzzer(1);
                }
            }
        }
    }
        // Alert when sensor node exceed configured value with ways below
    private void alert(SensorNode sensorNode, boolean isActive){
        if (alertTypeHashMap.get("InternetAlert")== true && isActive == true) {
            new FCMServerThread("Sensor Node","Exceed your setting, at node: " + sensorNode.getID()+", at zone: " + sensorNode.getZone());
        }
        // Access PowDev Node has GSM Module
        if (alertTypeHashMap.get("SMSAlert") == true && isActive == true){
            powDevNodeHashMap.get(MACAddrGSMNode).setSim0(1);
        } else {
            powDevNodeHashMap.get(MACAddrGSMNode).setSim0(0);
        }
        // Access PowDev Node has GSM Module
        if (alertTypeHashMap.get("CallAlert")== true && isActive == true){
            powDevNodeHashMap.get(MACAddrGSMNode).setSim1(1);
        } else {
            powDevNodeHashMap.get(MACAddrGSMNode).setSim1(0);
        }
    }
        // The public method is called in SocketServerThread
    public void checkSystem(HashMap<String, SensorNode> sensorNodeHashMap,
                            HashMap<String, PowDevNode> powDevNodeHashMap ){
        this.sensorNodeHashMap = sensorNodeHashMap;
        this.powDevNodeHashMap = powDevNodeHashMap;
        this.checkAllSensorNode();
        Log.d(TAG,"Checked System");
    }
}
