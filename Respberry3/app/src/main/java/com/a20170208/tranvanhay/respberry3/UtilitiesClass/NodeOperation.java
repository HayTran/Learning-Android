package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

import android.icu.text.LocaleDisplayNames;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by cente on 14-Jul-17.
 */

public class NodeOperation {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = NodeOperation.class.getSimpleName();
    private static final long SPACE_TIME_ALERT_GSM = 5000;
    public NodeOperation() {
    }
    public void execute(){
        mHandler.post(checkNodes);
    }
    private Handler mHandler = new Handler();
    private Runnable checkNodes = new Runnable() {
        @Override
        public void run() {
            /**
             * Check whether or not node is existing
             */
            HashMap <String,SensorNode> sensorNodeHashMap = SocketServerThread.sensorNodeHashMap;
            HashMap <String,PowDevNode> powdevNodeHashMap = SocketServerThread.powdevNodeHashMap;
            for (SensorNode sensorNode : sensorNodeHashMap.values()) {
                if (sensorNode.getTimeSend()!= 0
                        &&  System.currentTimeMillis() - sensorNode.getTimeSend() > 10000) {
                    Log.d(TAG,"Node corrupted! ID: " + sensorNode.getID());
                    sendNotificationToFCM(sensorNode.getID());
                }
            }
            for (PowDevNode powDevNode : powdevNodeHashMap.values()) {
                if (powDevNode.getTimeOperation() != 0
                        &&  System.currentTimeMillis() - powDevNode.getTimeOperation() > 10000) {
                    sendNotificationToFCM(powDevNode.getID());
                }
            }

            /**
             * Check and adjust GSM in PowDev node
             */
            try {
                if (SystemManagement.isActive == false) {
                    if (SystemManagement.lastTimeSendGSM0 + SPACE_TIME_ALERT_GSM < System.currentTimeMillis()) {
                        powdevNodeHashMap.get(SystemManagement.MACAddrGSMNode).setSim0(0);
                    }
                    if (SystemManagement.lastTimeSendGSM1 + SPACE_TIME_ALERT_GSM < System.currentTimeMillis()) {
                        powdevNodeHashMap.get(SystemManagement.MACAddrGSMNode).setSim1(0);
                    }
                    Log.d(TAG,"Already turn off GSM");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            mHandler.postDelayed(checkNodes,5000);
        }
    };

    private void sendNotificationToFCM(String title){
        new FCMServerThread(title, "Ngắt kết nối!!!").start();
        mData.child(FirebasePath.ALERT_DATABASE_PATH).child(System.currentTimeMillis()+"").setValue(title + " Ngắt kết nối!!!");
    }


}
