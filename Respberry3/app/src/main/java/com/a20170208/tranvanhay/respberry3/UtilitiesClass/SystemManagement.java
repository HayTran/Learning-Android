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
    private boolean callAlert, SMSAlert, internetAlert;
        // Flag to separate with user's affect
    private boolean alreadySim0Alert = false, alreadySim1Alert = false, alreadyImplement = false;
    String MACAddrGSMNode;
    private int selectionAlertNumber, selectionControlNumber, selectionBothNumber;

    public SystemManagement() {
        this.getControllerConfig();
        this.conditionHashMap = new HashMap<>();
    }

    private void getControllerConfig(){
        mData.child(FirebasePath.CONTROLLER_CONDITION_CONFIG_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    // Clear hashmap before put new value into hashmap
                conditionHashMap.clear();
                selectionAlertNumber = 0;
                selectionControlNumber  = 0;
                selectionBothNumber = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
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
                    if (value ==  100) {
                        selectionAlertNumber++;
                    } else if (value == 200) {
                        selectionControlNumber++;
                    } else if (value == 300) {
                        selectionBothNumber++;
                    }
                }
                Log.d(TAG,"Get value config for sensor with:"
                        +"\nselectionAlertNumber=" + selectionAlertNumber
                        +"\nselectionControlNumber=" + selectionControlNumber
                        +"\nselectionBothNumber=" + selectionBothNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    // Clear hashmap before put new value into hashmap
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    boolean value = Boolean.valueOf(dataSnapshot1.getValue().toString());
                    if (dataSnapshot1.getKey().equals("SMSAlert")) {
                        SMSAlert = value;
                    }   else if (dataSnapshot1.getKey().equals("CallAlert")) {
                        callAlert  = value;
                    }   else if (dataSnapshot1.getKey().equals("InternetAlert")) {
                        internetAlert = value;
                    }
                }
                Log.d(TAG,"Got alert type config with: " +
                        "\nSMSAlert = " + SMSAlert +
                        "\nCallAlert = " + callAlert +
                        "\nInternetAlert = " + internetAlert);
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
                // Check alert and both type
            if (checkEachSensorNode(sensorNode,100) || checkEachSensorNode(sensorNode,300)){
                alert(sensorNode, true);
            } else {
                alert(sensorNode, false);
            }
                // Check control powdev and both type
            if (checkEachSensorNode(sensorNode,200) || checkEachSensorNode(sensorNode,300)){
                controlPowDev(sensorNode,true);
            } else {
                controlPowDev(sensorNode,false);
            }
        }
    }
        // Check each sensor node in the system corressponding with typeOperation
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
        Log.d(TAG,"Count = " + count
                +"\nselectionAlertNumber = " + selectionAlertNumber
                +"\nselectionControlNumber = " +selectionControlNumber
                +"\nselectionBothNumber = " + selectionBothNumber
                +"\ntypeOperation = " + typeOperation);
        if (typeOperation == 100 && count == selectionAlertNumber && count > 0) {
            Log.d(TAG,"checkEachSensorNode is: true with Alert" );
            return true;
        }   else if (typeOperation == 200 && count == selectionControlNumber && count > 0){
            Log.d(TAG,"checkEachSensorNode is: true with Control" );
            return true;
        }   else if (typeOperation == 300 && count == selectionBothNumber && count > 0){
            Log.d(TAG,"checkEachSensorNode is: true with Alert and Control" );
            return true;
        } else return false;

    }
        // Control PowDev when sensor node exceed configured value
    private void controlPowDev(SensorNode sensorNode, boolean isActive ){
        int zone = sensorNode.getZone();
        if (isActive) {
            for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
                if (powDevNode.getZone() == zone && powDevNode.isEnable()) {
                    powDevNode.implementTask(0);
                    powDevNode.setAlreadyImplement(true);
                }
            }
        } else {
            for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
                if (powDevNode.getZone() == zone && powDevNode.isAlreadyImplemented() && powDevNode.isEnable()) {
                    powDevNode.implementTask(1);
                    powDevNode.setAlreadyImplement(false);
                }
            }
        }
        Log.d(TAG,"Control PowDev is actived?: " + isActive +", at sensor node: " + sensorNode.getID());
    }
        // Alert when sensor node exceed configured value with ways below
    private void alert(SensorNode sensorNode, boolean isActive){
        if (internetAlert == true && isActive == true) {
            new FCMServerThread("Sensor Node","Exceed your setting, at node: "
                    + sensorNode.getID()+", at zone: " + sensorNode.getZone()
                    + "\nDetail: " + sensorNode.toString()).start();
            Log.d(TAG,"Sent message to FCM");
        }
            // Access PowDev Node has GSM Module
        if (SMSAlert == true && isActive == true){
            powDevNodeHashMap.get(MACAddrGSMNode).setSim0(1);
            alreadySim0Alert = true;
        }   else if (alreadySim0Alert == true) {
                // Just invert it's state when it's 1
            powDevNodeHashMap.get(MACAddrGSMNode).setSim0(0);
            alreadySim0Alert = false;
        }
            // Access PowDev Node has GSM Module
        if (callAlert == true && isActive == true){
            powDevNodeHashMap.get(MACAddrGSMNode).setSim1(1);
            alreadySim1Alert = true;
        }   else if (alreadySim1Alert == true) {
                // Just invert it's state when it's 1
            powDevNodeHashMap.get(MACAddrGSMNode).setSim1(0);
            alreadySim1Alert = false;
        }
        Log.d(TAG,"Alert is active?: " +isActive + ", at sensor node: " + sensorNode.getID());
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
