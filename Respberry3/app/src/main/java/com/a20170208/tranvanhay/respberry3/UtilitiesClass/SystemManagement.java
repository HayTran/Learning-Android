package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

import android.os.Handler;
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
    private static final long SPACE_TIME_ALERT_FCM = 5000;
    private static final long SPACE_TIME_ALERT_GSM = 5000;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    HashMap <String,SensorNode> sensorNodeHashMap;
    HashMap <String,PowDevNode> powDevNodeHashMap;
    HashMap <String, Integer > conditionHashMap;
    public boolean callAlert, SMSAlert, internetAlert, autoOperation;
    static boolean isAllAlert, isMQ2Alert, isHumidityAlert, isActive;
        // Flag to separate with user's affect
    static String MACAddrGSMNode;
    private long lastTimeAlertInternet;
    static long lastTimeSendGSM0, lastTimeSendGSM1 ;

    public SystemManagement() {
        this.getControllerConfig();
        this.conditionHashMap = new HashMap<>();
    }

    private void getControllerConfig(){
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
        mData.child(FirebasePath.CONTROLLER_AUTO_OPERATION_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                autoOperation = Boolean.valueOf(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
        // Check all sensor node and powdev in the system
    private void checkAllSensorNode(){
        for (SensorNode sensorNode : sensorNodeHashMap.values()) {
            checkEachSensorNode(sensorNode);
        }
        for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
            checkEachPowDevNode(powDevNode);
        }
    }
        // Check each sensor node in the system corressponding with typeOperation
    private void checkEachSensorNode(SensorNode sensorNode){
        int count = 0;
        StringBuilder exceedString = new StringBuilder();
        exceedString.append("Các cảm biến vượt quá giới hạn: ");
        if (sensorNode.getTemperature() >= sensorNode.getConfigTemperature()){
            count++;
            exceedString.append("\nNhiệt độ: " + sensorNode.getTemperature());
        }
        if (sensorNode.getMeanFlameValue() >= sensorNode.getConfigMeanFlameValue()){
            count++;
            exceedString.append("\nLửa: " + sensorNode.getMeanFlameValue());
        }
        if (sensorNode.getLightIntensity() >= sensorNode.getConfigLightIntensity()){
            count++;
            exceedString.append("\nÁnh sáng: " + sensorNode.getLightIntensity());
        }
        if (sensorNode.getMQ2() >= sensorNode.getConfigMQ2()){
            count++;
            exceedString.append("\nKhí dễ cháy: " + sensorNode.getMQ2());
        }
        if (sensorNode.getMQ7() >= sensorNode.getConfigMQ7()) {
            count++;
            exceedString.append("\nKhí CO: " + sensorNode.getMQ7());
        }

            // Check threshold
        if (count >= 2 && count < 4) {
            sensorNode.setExceedAlertCount(sensorNode.getExceedAlertCount() + 1);
            sensorNode.setExceedImplementCount(0);
        } else if (count >= 4) {
            sensorNode.setExceedImplementCount(sensorNode.getExceedImplementCount() + 1);
        } else {
            sensorNode.setExceedImplementCount(0);
            sensorNode.setExceedAlertCount(0);
        }
            // Just debug
        Log.d(TAG,"count = " + count);
        Log.d(TAG,"ExceedAlert: " + sensorNode.getExceedAlertCount());
        Log.d(TAG,"ExceedImplement: " + sensorNode.getExceedImplementCount());

            // Check threshold alert
        if (sensorNode.getMQ2() >= sensorNode.getConfigMQ2()) {
            sensorNode.setExceedAlertMQ2Count(sensorNode.getExceedAlertMQ2Count() + 1);
        }   else {
            sensorNode.setExceedAlertMQ2Count(0);
        }
        if (sensorNode.getHumidity() >= sensorNode.getConfigHumidity()) {
            sensorNode.setExceedAlertHumidityCount(sensorNode.getExceedAlertHumidityCount() + 1);
        }   else {
            sensorNode.setExceedAlertHumidityCount(0);
        }

        /**
         * Implement alert
         */
            // Check all events
        if (sensorNode.getExceedAlertCount() >= 5) {
            isAllAlert = true;
        }   else {
            isAllAlert = false;
        }
        if (sensorNode.getExceedAlertMQ2Count() >= 5 ) {
            exceedString.append("\nKhí gas: " + sensorNode.getMQ2());
            isMQ2Alert = true;
        }   else {
            isMQ2Alert = false;
        }
        if (sensorNode.getExceedAlertHumidityCount() >= 5) {
            exceedString.append("\nĐộ ẩm: " + sensorNode.getHumidity());
            isHumidityAlert = true;
        }   else {
            isHumidityAlert = false;
        }
            // Implement alert
        if (isAllAlert || isMQ2Alert || isHumidityAlert){
            alert(sensorNode,exceedString.toString());
            isActive = true;
        } else {
            isActive = false;
        }

        /**
         * Implement control
         */
        if (sensorNode.getExceedImplementCount() >= 5) {
            controlPowDev(sensorNode,true);
        }   else {
            controlPowDev(sensorNode,false);
        }

    }

    private void checkEachPowDevNode(PowDevNode powDevNode){
        if (powDevNode.getAlarm() == 1) {
            String body_message;
            body_message = "Nút khẩn cấp được kích hoạt: "
                    + powDevNode.getID()+", tại khu vực: " + powDevNode.getZone();
            new FCMServerThread("PowDevNode",body_message).start();
            mData.child(FirebasePath.ALERT_DATABASE_PATH).child(System.currentTimeMillis()+"").setValue(body_message);
            Log.d(TAG,"Sent message to FCM");
        }
    }

        // Control PowDev when sensor node exceed configured value
    private void controlPowDev(SensorNode sensorNode, boolean isActive ){
        int zone = sensorNode.getZone();
        if (isActive) {
            for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
                if (powDevNode.getZone() == zone && powDevNode.isEnable() == true && autoOperation == true) {
                    powDevNode.autoImplementTask(0);
                }
            }
        } else {
            for (PowDevNode powDevNode : powDevNodeHashMap.values()) {
                if (powDevNode.getZone() == zone && powDevNode.isEnable() == true && autoOperation == true) {
                    powDevNode.autoImplementTask(1);
                }
            }
        }
    }
        // Alert when sensor node exceed configured value with ways below
    private void alert(SensorNode sensorNode, String messageContent){
        if (internetAlert == true) {
            if (System.currentTimeMillis() > lastTimeAlertInternet + SPACE_TIME_ALERT_FCM) {
                lastTimeAlertInternet = System.currentTimeMillis();
                String body_message;
                body_message = "Nút cảm biến vượt ngưỡng: "
                        + sensorNode.getID()+", tại khu vực: " + sensorNode.getZone()
                        + "\nChi tiết: " + messageContent;
                new FCMServerThread("Sensor Node",body_message).start();
                mData.child(FirebasePath.ALERT_DATABASE_PATH).child(System.currentTimeMillis()+"").setValue(body_message);
                Log.d(TAG,"Sent message to FCM");
            }
        }
            // Access PowDev Node has GSM Module
        if (SMSAlert == true){
            powDevNodeHashMap.get(MACAddrGSMNode).setSim0(1);
            lastTimeSendGSM0 = System.currentTimeMillis();
        }
            // Access PowDev Node has GSM Module
        if (callAlert == true){
            powDevNodeHashMap.get(MACAddrGSMNode).setSim1(1);
            lastTimeSendGSM1 = System.currentTimeMillis();
        }
        Log.d(TAG,"Alert is already implemented");
    }
        // The public method is called in SocketServerThread
    public void checkSystem(HashMap<String, SensorNode> sensorNodeHashMap,
                            HashMap<String, PowDevNode> powDevNodeHashMap ){
        this.sensorNodeHashMap = sensorNodeHashMap;
        this.powDevNodeHashMap = powDevNodeHashMap;
        this.checkAllSensorNode();
    }

}
