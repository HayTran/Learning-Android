package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

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
    static final int SocketServerPORT = 4567;
    private long timeSaveInDatabase = 0;
    int count = 0;
    private boolean isGottenConfig0 = false, isGottenConfig1 = false;
    HashMap <String,String> MACAddrAndIDHashMap = new HashMap<>();  // help server recognize || help user recognize
    static HashMap <String,SensorNode> sensorNodeHashMap = new HashMap<>();
    static HashMap <String,PowDevNode> powdevNodeHashMap = new HashMap<>();
    ServerSocket serverSocket;
    SystemManagement systemManagement = new SystemManagement();
    public SocketServerThread (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    private void getConfig() {
        mData.child(FirebasePath.MACADDR_AND_ID_MAPPING_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MACAddrAndIDHashMap.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MACAddrAndIDHashMap.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                }
                isGottenConfig0 = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(FirebasePath.TIME_SAVE_IN_DATABASE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timeSaveInDatabase = Long.valueOf(dataSnapshot.getValue().toString());
                isGottenConfig1 = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void run() {
        getConfig();
        try {
            serverSocket = new ServerSocket(SocketServerPORT);
            while (true) {
                    // Must gotten all config then start server
                if (isGottenConfig0 == true && isGottenConfig1 == true) {
                    Socket socket = serverSocket.accept();
                    // Set time out for not delay
                    socket.setSoTimeout(2000);
                    // Initialize a SocketServerReplyThread object
                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                            socket, count);
                    // Start running Server Reply Thread
                    socketServerReplyThread.start();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            mData.child("Server Socket Accept Error").push().setValue(e.toString() + " " + TimeAndDate.currentTime);
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
            private static final int SUCCESS_SESSION_FLAG = 150;
            private static final int FAILED_SESSION_FLAG = 160;
                // Flags for sensor
            private static final int BEGIN_SESSION_SENSOR_BYTE = 19;            // 17 byte data, 1 flag, 1 its data
            private static final int FIRST_CONFIRM_SESSION_SENSOR_BYTE = 18;
            private static final int SECOND_CONFRIM_SESSION_SENSOR_BYTE = 1;
                // Flags for powdev
            private static final int BEGIN_SESSION_POWDEV_BYTE = 9;             // 2 byte flag and its capacity and 7 byte data
            private static final int FIRST_CONFIRM_SESSION_POWDEV_BYTE = 7;     // 6 byte data, 1 flag
            private static final int SECOND_CONFRIM_SESSION_POWDEV_BYTE = 8;    // 6 byte data, 1 flag, 1 its capacity
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
                            int [] arrayBytes = new int[BEGIN_SESSION_SENSOR_BYTE - 2]; // Subtract 2 byte flags, and its capacity
                            for (int i = 0 ; i < arrayBytes.length; i++) {
                                arrayBytes[i] = dIn.readUnsignedByte();
                            }
                                // Check to create a new object. If it's existing, just update arrayByte and isConfirmed
                            String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());
                            if (isSensorNodeObjectExisting(MACAddr) == false) {
                                String ID = MACAddrAndIDHashMap.get(MACAddr);
                                SensorNode sensorNode = new SensorNode(MACAddr,ID,arrayBytes);
                                sensorNodeHashMap.put(MACAddr, sensorNode);
                            } else {
                                sensorNodeHashMap.get(MACAddr).setArrayBytes(arrayBytes);
                            }
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
                                // Read info from node pow dev
                            int [] arrayBytes = new int [BEGIN_SESSION_POWDEV_BYTE - 2];    // Subtract 2 byte flags and its capacity
                            for (int i = 0; i < arrayBytes.length; i ++) {
                                arrayBytes[i] = dIn.readUnsignedByte();
                            }
                                // Check to create a new object. If it's existing, just update strengthWifi
                            String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());
                            if (isPowDevNodeObjectExisting(MACAddr) == false) {
                                String ID = MACAddrAndIDHashMap.get(MACAddr);
                                PowDevNode powDevNode = new PowDevNode(MACAddr,ID,arrayBytes);
                                powdevNodeHashMap.put(MACAddr, powDevNode);
                            } else {
                                powdevNodeHashMap.get(MACAddr).setStrengthWifi(arrayBytes[0]);
                                powdevNodeHashMap.get(MACAddr).setAlarm(arrayBytes[6]);
                            }
                                // Reply to client
                            dOut.writeByte(FIRST_CONFIRM_SESSION_FLAG);
                            dOut.writeByte(powdevNodeHashMap.get(MACAddr).getDev0());
                            dOut.writeByte(powdevNodeHashMap.get(MACAddr).getDev1());
                            dOut.writeByte(powdevNodeHashMap.get(MACAddr).getBuzzer());
                            dOut.writeByte(powdevNodeHashMap.get(MACAddr).getSim0());
                            dOut.writeByte(powdevNodeHashMap.get(MACAddr).getSim1());
                            dOut.writeByte(powdevNodeHashMap.get(MACAddr).getAlarm());
                        }
                    }   // Second confirm session flag
                    else if (firstByteReceive == SECOND_CONFIRM_SESSION_FLAG){
                        /**
                         * if SECOND_SESSION_SENSOR_BYTE corresponding with Node Sensor
                         */
                        if (secondByteReceive == SECOND_CONFRIM_SESSION_SENSOR_BYTE) {
                            int resultCode = dIn.readUnsignedByte();
                            if (resultCode == SUCCESS_SESSION_FLAG) {
                                String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());
                                    // Send current value to database
                                sensorNodeHashMap.get(MACAddr).setTimeSend(System.currentTimeMillis());
                                sensorNodeHashMap.get(MACAddr).sendToFirebase();

                                    // Check whether or not save in firebase database
                                if (sensorNodeHashMap.get(MACAddr).getTimeSaveInDatabase() + timeSaveInDatabase <=
                                        System.currentTimeMillis()) {
                                    sensorNodeHashMap.get(MACAddr).saveInDatabaseInFirebase();
                                    sensorNodeHashMap.get(MACAddr).setTimeSaveInDatabase(System.currentTimeMillis());
                                }
                                    // Call SystemManagement
                                systemManagement.checkSystem(sensorNodeHashMap,powdevNodeHashMap);
                            } else if (resultCode == FAILED_SESSION_FLAG) {
                                Log.d(TAG,"Node Sensor session failed at " + TimeAndDate.currentTime);
                            }
                        }
                        /**
                         * if SECOND_SESSION_SENSOR_BYTE corresponding with Node PowDev
                         */
                        else if (secondByteReceive == SECOND_CONFRIM_SESSION_POWDEV_BYTE) {
                            int client0 = 0, client1 = 0, client2 = 0, client3 = 0, client4 = 0, client5 = 0;
                            int server0 = 0, server1 = 0, server2 = 0, server3 = 0, server4 = 0, server5 = 0;
                            client0 = dIn.readUnsignedByte();
                            client1 = dIn.readUnsignedByte();
                            client2 = dIn.readUnsignedByte();
                            client3 = dIn.readUnsignedByte();
                            client4 = dIn.readUnsignedByte();
                            client5 = dIn.readUnsignedByte();
                            String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());
                            server0 = powdevNodeHashMap.get(MACAddr).getDev0();
                            server1 = powdevNodeHashMap.get(MACAddr).getDev1();
                            server2 = powdevNodeHashMap.get(MACAddr).getBuzzer();
                            server3 = powdevNodeHashMap.get(MACAddr).getSim0();
                            server4 = powdevNodeHashMap.get(MACAddr).getSim1();
                            server5 = powdevNodeHashMap.get(MACAddr).getAlarm();
                                // Check data's local and data's client, if true, reply success session to client, otherwise reply fail session
                            if (client0==server0&&client1==server1&&client2==server2
                                    &&client3==server3&&client4==server4&&client5==server5){
                                dOut.writeByte(END_CONFIRM_SESSION_FLAG);
                                dOut.writeByte(SUCCESS_SESSION_FLAG);
                                powdevNodeHashMap.get(MACAddr).notifyLastestTimeOperation();
                                    // Confirm that powdev node implemented
                                powdevNodeHashMap.get(MACAddr).setAlreadyImplemented(true);
                                    // Call SystemManagement
                                systemManagement.checkSystem(sensorNodeHashMap,powdevNodeHashMap);
                            } else {
                                dOut.writeByte(END_CONFIRM_SESSION_FLAG);
                                dOut.writeByte(FAILED_SESSION_FLAG);
                                Log.d(TAG,"Node PowDev session failed at " + TimeAndDate.currentTime);
                            }
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
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
            private boolean isPowDevNodeObjectExisting(String MACAddr){
                for (PowDevNode powDevNode : powdevNodeHashMap.values()){
                    if (powDevNode.getMACAddr().equals(MACAddr)) {
                        return true;
                    }
                }
                return false;
            }
            private boolean isSensorNodeObjectExisting(String MACAddr){
                for (SensorNode sensorNode : sensorNodeHashMap.values()){
                    if (sensorNode.getMACAddr().equals(MACAddr)) {
                        return true;
                    }
                }
                return false;
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



