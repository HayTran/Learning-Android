package com.a20170208.tranvanhay.respberry3;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by Tran Van Hay on 3/3/2017.
 */

public class SocketServerThread extends Thread {
    private static final String TAG = SocketServerThread.class.getSimpleName();
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    static final int SocketServerPORT = 8080;
    int count = 0;
    HashMap <String,NodeSensor> nodeSensorHashMap = new HashMap<>();
    ServerSocket serverSocket;
    SocketServerThread (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SocketServerPORT);
            mData.child("SocketServer").child("zNotify").setValue("IP:"+this.getIpAddress()+":"+serverSocket.getLocalPort());
            while (true) {
                count ++;
                if(count >=255){
                    count = 0;
                }
                Socket socket = serverSocket.accept();
                    // Initialize a SocketServerReplyThread object
                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                        socket, count);
                    // Start running Server Reply Thread
                socketServerReplyThread.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            mData.child("Server Socket Accept Error").push().setValue(e.toString() + " " + TimeAnDate.currentTimeOffline);
            Log.d(TAG, "Exception Catched: " + e.toString());
            e.printStackTrace();
        }
    }

        // ReplyThreadFromServer Class
    class SocketServerReplyThread extends Thread {
            private int firstByteReceive = 0;
            private int secondByteReceive = 0;
            private static final int BEGIN_SESSION_FLAG = 110;
            private static final int FIRST_CONFIRM_SESSION_FLAG = 120;
            private static final int SECOND_CONFIRM_SESSION_FLAG = 130;
            private static final int END_CONFIRM_SESSION_FLAG = 140;
            private static final int BEGIN_SESSION_SENSOR_BYTE = 19;
            private static final int RESULT_SESSION_FLAG = 150;
            private static final int FIRST_CONFIRM_SENSOR_SESSION_BYTE = 20;
            private static final int SECOND_CONFRIM_SENSOR_SESSION_BYTE = 1;
            private int strengthWifi = 0;
            private int temperature = 0, humidity = 0;
            private int lightIntensity0 = 0, lightIntensity1 = 0;
            private int flameValue0_0 = 0, flameValue0_1 = 0,flameValue1_0 = 0, flameValue1_1 = 0;
            private int flameValue2_0 = 0, flameValue2_1 = 0,flameValue3_0 = 0, flameValue3_1 = 0;
            private int mq2Value0 = 0, mq2Value1 = 0, mq7Value0 = 0, mq7Value1 = 0;
            private double flameValue0 = 0, flameValue1 = 0, flameValue2 = 0, flameValue3 = 0, lightIntensity = 0, mq2Value = 0, mq7Value = 0;
            private Socket hostThreadSocket;  //this object specify whether this socket of which host
            int cnt;
            private NodeSensor nodeSensor;
            SocketServerReplyThread(Socket socket, int c) {
                hostThreadSocket = socket;
                cnt = c;
            }
            @Override
            public void run() {
                Log.d(TAG, "======================================================== Count = " + cnt);
                // Create a message to Client's socket
                DataOutputStream dOut = null;
                DataInputStream dIn = null;
                try {
                    dIn = new DataInputStream(hostThreadSocket.getInputStream());
                    dOut = new DataOutputStream(hostThreadSocket.getOutputStream());
                    // Read data which sent from client
                    firstByteReceive = dIn.readUnsignedByte();
                    secondByteReceive = dIn.readUnsignedByte();
                    if (firstByteReceive == BEGIN_SESSION_FLAG) {
                        if (secondByteReceive == BEGIN_SESSION_SENSOR_BYTE){ // 17 corresponding with sensor node
                                // Read sensor data
                            int [] arrayBytes = new int[BEGIN_SESSION_SENSOR_BYTE];
                            for (int i = 0 ; i < arrayBytes.length; i++){
                                arrayBytes[i] = dIn.readUnsignedByte();
                            }
                                // Intialize a Node Sensor object without confirmed
                            String MACAddr = new ARPNetwork(hostThreadSocket.getInetAddress().getHostAddress()).findMAC();
                            nodeSensor = new NodeSensor(arrayBytes, MACAddr, false);
                            nodeSensor.convertValue();
                            Log.d(TAG,"Node sensor: " + nodeSensor.toString());
                            nodeSensorHashMap.put(MACAddr,nodeSensor);
                                // Reply to client data already received.
                            dOut.writeByte(FIRST_CONFIRM_SESSION_FLAG);
                            for (int i = 0 ; i < arrayBytes.length; i++){
                                dOut.writeByte(arrayBytes[i]);
                            }
                        }
                    } else if (firstByteReceive == SECOND_CONFIRM_SESSION_FLAG){
                        if (secondByteReceive == SECOND_CONFRIM_SENSOR_SESSION_BYTE) {
                            Log.d(TAG,"Result code: " + dIn.readUnsignedByte());
                            String MACAddr = new ARPNetwork(hostThreadSocket.getInetAddress().getHostAddress()).findMAC();
                            nodeSensorHashMap.get(MACAddr).convertValue();
                            Log.d(TAG,"Node sensor: " + nodeSensor.toString());
                        }
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    mData.child("Server Socket Read and Reply Error").push().setValue(e.toString() + " " + TimeAnDate.currentTimeOffline);
                    e.printStackTrace();
                    Log.d(TAG, "Exception Catched: " + e.toString());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        dOut.close();
                        dIn.close();
                        hostThreadSocket.close();
                        Log.d(TAG, "Close Input, Output Stream, Socket");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "Exception Catched: " + e.toString());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    // Get Server's IP waiting socket coming
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ip += ""
                                + inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            mData.child("Error").setValue(e.toString());
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}



