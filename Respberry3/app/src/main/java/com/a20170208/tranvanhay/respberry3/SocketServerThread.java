package com.a20170208.tranvanhay.respberry3;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    int D1 = 0 ,D2 = 0 , D3 = 0, D5 = 0;
    HashMap <String,NodeSensor> nodeSensorHashMap = new HashMap<>();
    ServerSocket serverSocket;
    SocketServerThread (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        triggerPowDev();
        try {
            serverSocket = new ServerSocket(SocketServerPORT);
            mData.child("SocketServer").child("zNotify").setValue("IP:"+this.getIpAddress()+":"+serverSocket.getLocalPort());
            while (true) {
                count ++;
                if(count >=255){
                    count = 0;
                }
                Socket socket = serverSocket.accept();
                    // Set time out for not delay
                socket.setSoTimeout(3000);
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
    private void triggerPowDev(){
        mData.child("At PowDev").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals("D1")) {
                        D1 = Integer.valueOf(dataSnapshot1.getValue().toString());
                    }
                    if (dataSnapshot1.getKey().equals("D2")) {
                        D2 =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    }
                    if (dataSnapshot1.getKey().equals("D3")){
                        D3 =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    }
                    if (dataSnapshot1.getKey().equals("D5")){
                        D5 =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
        // ReplyThreadFromServer Class
    class SocketServerReplyThread extends Thread {
            private int firstByteReceive = 0;
            private int secondByteReceive = 0;
            private static final int BEGIN_SESSION_FLAG = 110;
            private static final int FIRST_CONFIRM_SESSION_FLAG = 120;
            private static final int SECOND_CONFIRM_SESSION_FLAG = 130;
            private static final int END_CONFIRM_SESSION_FLAG = 140;
            private static final int SUCCESS_SESSION_FLAG = 150;
            private static final int FAILED_SESSION_FLAG = 160;
                // Flags for sensor
            private static final int BEGIN_SESSION_SENSOR_BYTE = 19;            // 17 byte data, 1 flag, 1 its data
            private static final int FIRST_CONFIRM_SESSION_SENSOR_BYTE = 18;
            private static final int SECOND_CONFRIM_SESSION_SENSOR_BYTE = 1;
                // Flags for powdev
            private static final int BEGIN_SESSION_POWDEV_BYTE = 2;             // 1 byte flag and its contain
            private static final int FIRST_CONFIRM_SESSION_POWDEV_BYTE = 5;     // 4 byte data, 1 flag
            private static final int SECOND_CONFRIM_SESSION_POWDEV_BYTE = 6;    // 4 byte data, 1 flag, 1 its contain
            private static final int END_CONFIRM_SESSION_POWDEV_BYTE = 140;
            private Socket hostThreadSocket;  //this object specify whether this socket of which host
            int cnt;
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
                    // Read 2 flags
                    firstByteReceive = dIn.readUnsignedByte();
                    secondByteReceive = dIn.readUnsignedByte();
                        // Begin session
                    if (firstByteReceive == BEGIN_SESSION_FLAG) {
                        /**
                         * if BEGIN_SESSION_SENSOR_BYTE corresponding with Node Sensor
                         */
                        if (secondByteReceive == BEGIN_SESSION_SENSOR_BYTE){
                                // Read sensor data
                            int [] arrayBytes = new int[BEGIN_SESSION_SENSOR_BYTE - 2]; // Subtract 2 byte flags, and number of bytes will send
                            for (int i = 0 ; i < arrayBytes.length; i++){
                                arrayBytes[i] = dIn.readUnsignedByte();
                            }
                                // Intialize a Node Sensor object without confirmed
                            String MACAddr = new ARPNetwork(hostThreadSocket.getInetAddress().getHostAddress()).findMAC();
                            NodeSensor nodeSensor = new NodeSensor(arrayBytes, MACAddr, false);
                            nodeSensor.convertValue();
                            Log.d(TAG,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Begin session: ");
                            Log.d(TAG,"Node sensor: " + nodeSensor.toString());
                            nodeSensorHashMap.put(MACAddr,nodeSensor);
                                // Reply to client data already received.
                            dOut.writeByte(FIRST_CONFIRM_SESSION_FLAG);
                            for (int i = 0 ; i < arrayBytes.length; i++){
                                dOut.writeByte(arrayBytes[i]);
                            }
                        }
                        /**
                         * if BEGIN_SESSION_SENSOR_BYTE corresponding with Node PowDev
                         */
                        else if (secondByteReceive == BEGIN_SESSION_POWDEV_BYTE) {
                            int strengthWifi = dIn.readUnsignedByte();
                            Log.d(TAG,"StrengthWifi: " + strengthWifi);
                            dOut.writeByte(FIRST_CONFIRM_SESSION_FLAG);
                            dOut.writeByte(D1);
                            dOut.writeByte(D2);
                            dOut.writeByte(D3);
                            dOut.writeByte(D5);
                        }
                    }   // Second confirm session flag
                    else if (firstByteReceive == SECOND_CONFIRM_SESSION_FLAG){
                        /**
                         * if BEGIN_SESSION_SENSOR_BYTE corresponding with Node Sensor
                         */
                        if (secondByteReceive == SECOND_CONFRIM_SESSION_SENSOR_BYTE) {
                            Log.d(TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ First confirm session: ");
                            Log.d(TAG,"Result code: " + dIn.readUnsignedByte());
                            String MACAddr = new ARPNetwork(hostThreadSocket.getInetAddress().getHostAddress()).findMAC();
                            nodeSensorHashMap.get(MACAddr).setConfirmed(true);
                            nodeSensorHashMap.get(MACAddr).setSendTime(TimeAnDate.currentTimeOffline);
                            Log.d(TAG,"Size: " + nodeSensorHashMap.size());
                            Log.d(TAG,"Node sensor: " + nodeSensorHashMap.get(MACAddr).toString());
                            checkSendDataToServer();
                        }
                        /**
                         * if BEGIN_SESSION_SENSOR_BYTE corresponding with Node PowDev
                         */
                        else if (secondByteReceive == SECOND_CONFRIM_SESSION_POWDEV_BYTE) {
                                int tmpD1 = 0, tmpD2 = 0, tmpD3 = 0, tmpD5 = 0;
                                tmpD1 = dIn.readUnsignedByte();
                                tmpD2 = dIn.readUnsignedByte();
                                tmpD3 = dIn.readUnsignedByte();
                                tmpD5 = dIn.readUnsignedByte();
                                if (tmpD1 == D1 && tmpD2 == D2 && tmpD3 == D3 && tmpD5 == D5 ){
                                    dOut.writeByte(END_CONFIRM_SESSION_FLAG);
                                    dOut.writeByte(SUCCESS_SESSION_FLAG);
                                } else {
                                    dOut.writeByte(END_CONFIRM_SESSION_FLAG);
                                    dOut.writeByte(FAILED_SESSION_FLAG);
                                }
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
            private void checkSendDataToServer(){
                for (NodeSensor nodeSensor : nodeSensorHashMap.values()){
                    if (nodeSensor.isConfirmed()) {
                        nodeSensor.sendToFirebase();
                        nodeSensor.setConfirmed(false);
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



