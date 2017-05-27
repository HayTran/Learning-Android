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
    HashMap <String,String> MACAddrAndIDHashMap = new HashMap<>();  // help server recognize || help user recognize
    HashMap <String,NodeSensor> nodeSensorHashMap = new HashMap<>();
    HashMap <String,NodePowDev> nodePowDevHashMap = new HashMap<>();
    ServerSocket serverSocket;
    SocketServerThread (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        mappingMACAddrAndID();
        try {
            serverSocket = new ServerSocket(SocketServerPORT);
            mData.child("SocketServer").child("zNotify").setValue("IP:"+this.getIpAddress()+":"+serverSocket.getLocalPort());
            while (true) {
                count ++;
                if(count >= 255){
                    count = 0;
                }
                Socket socket = serverSocket.accept();
                    // Set time out for not delay
                socket.setSoTimeout(2000);
                    // Initialize a SocketServerReplyThread object
                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                        socket, count);
                    // Start running Server Reply Thread
                socketServerReplyThread.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            mData.child("Server Socket Accept Error").push().setValue(e.toString() + " " + TimeAndDate.currentTime);
            Log.d(TAG, "Exception Catched: " + e.toString());
            e.printStackTrace();
        }
    }
    private void mappingMACAddrAndID() {
        /**
         *          When debug
         */
//        MACAddrAndIDHashMap.put("5c:cf:7f:ab:ac:81","NodeSensor0");
//        MACAddrAndIDHashMap.put("a0:20:a6:02:10:57","NodePowDev0");
      mData.child("SocketServer").child("MACAddrAndIDMapping").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              MACAddrAndIDHashMap.clear();
              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                  MACAddrAndIDHashMap.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
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
            private static final int BEGIN_SESSION_POWDEV_BYTE = 8;             // 1 byte flag and its capacity and 6 byte data
            private static final int FIRST_CONFIRM_SESSION_POWDEV_BYTE = 6;     // 5 byte data, 1 flag
            private static final int SECOND_CONFRIM_SESSION_POWDEV_BYTE = 7;    // 5 byte data, 1 flag, 1 its capacity
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
                            int [] arrayBytes = new int[BEGIN_SESSION_SENSOR_BYTE - 2]; // Subtract 2 byte flags, and its capacity
                            for (int i = 0 ; i < arrayBytes.length; i++) {
                                arrayBytes[i] = dIn.readUnsignedByte();
                            }
                                // Check to create a new object. If it's existing, just update arrayByte and isConfirmed
                            String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());
                            if (isNodeSensorObjectExisting(MACAddr) == false
                                    &&  MACAddrAndIDHashMap.get(MACAddr) != null ) {
                                    // MACAddrAndIDHashMap.get(MACAddr) != null to wait for Firebase got data
                                String ID = MACAddrAndIDHashMap.get(MACAddr);
                                NodeSensor nodeSensor = new NodeSensor(MACAddr,ID,arrayBytes);
                                nodeSensorHashMap.put(MACAddr,nodeSensor);
                            } else {
                                nodeSensorHashMap.get(MACAddr).setArrayBytes(arrayBytes);
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
                            if (isNodePowDevObjectExisting(MACAddr) == false
                                     &&  MACAddrAndIDHashMap.get(MACAddr) != null ) {
                                    // MACAddrAndIDHashMap.get(MACAddr) != null to wait for Firebase got data
                                String ID = MACAddrAndIDHashMap.get(MACAddr);
                                NodePowDev nodePowDev = new NodePowDev(MACAddr,ID,arrayBytes,true);
                                nodePowDevHashMap.put(MACAddr,nodePowDev);
                                Log.d("Nhung","Size: " + MACAddrAndIDHashMap.size());
                            } else {
                                nodePowDevHashMap.get(MACAddr).setStrengthWifi(arrayBytes[0]);
                            }
                                // Reply to client
                            dOut.writeByte(FIRST_CONFIRM_SESSION_FLAG);
                            dOut.writeByte(nodePowDevHashMap.get(MACAddr).getDev0());
                            dOut.writeByte(nodePowDevHashMap.get(MACAddr).getDev1());
                            dOut.writeByte(nodePowDevHashMap.get(MACAddr).getBuzzer());
                            dOut.writeByte(nodePowDevHashMap.get(MACAddr).getSim0());
                            dOut.writeByte(nodePowDevHashMap.get(MACAddr).getSim1());
                        }
                    }   // Second confirm session flag
                    else if (firstByteReceive == SECOND_CONFIRM_SESSION_FLAG){
                        /**
                         * if SECOND_SESSION_SENSOR_BYTE corresponding with Node Sensor
                         */
                        if (secondByteReceive == SECOND_CONFRIM_SESSION_SENSOR_BYTE) {
                            int resultCode = dIn.readUnsignedByte();
                            if (resultCode == SUCCESS_SESSION_FLAG) {
                                String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());;
                                nodeSensorHashMap.get(MACAddr).setTimeSend(TimeAndDate.currentTime);
                                nodeSensorHashMap.get(MACAddr).sendToFirebase();
                            } else if (resultCode == FAILED_SESSION_FLAG) {
                                Log.d(TAG,"Node Sensor session failed at " + TimeAndDate.currentTime);
                            }
                        }
                        /**
                         * if SECOND_SESSION_SENSOR_BYTE corresponding with Node PowDev
                         */
                        else if (secondByteReceive == SECOND_CONFRIM_SESSION_POWDEV_BYTE) {
                            int client0 = 0, client1 = 0, client2 = 0, client3 = 0, client4 = 0;
                            int server0 = 0, server1 = 0, server2 = 0, server3 = 0, server4 = 0;
                            client0 = dIn.readUnsignedByte();
                            client1 = dIn.readUnsignedByte();
                            client2 = dIn.readUnsignedByte();
                            client3 = dIn.readUnsignedByte();
                            client4 = dIn.readUnsignedByte();
                            String MACAddr = ARPNetwork.findMAC(hostThreadSocket.getInetAddress().getHostAddress());
                            server0 = nodePowDevHashMap.get(MACAddr).getDev0();
                            server1 = nodePowDevHashMap.get(MACAddr).getDev1();
                            server2 = nodePowDevHashMap.get(MACAddr).getBuzzer();
                            server3 = nodePowDevHashMap.get(MACAddr).getSim0();
                            server4 = nodePowDevHashMap.get(MACAddr).getSim1();
                                // Check data's local and data's client, if true, reply success session to client, otherwise reply fail session
                            if (client0==server0&&client1==server1&&client2==server2&&client3==server3&&client4==server4){
                                dOut.writeByte(END_CONFIRM_SESSION_FLAG);
                                dOut.writeByte(SUCCESS_SESSION_FLAG);
                                nodePowDevHashMap.get(MACAddr).notifyLastestTimeOperation();
                            } else {
                                dOut.writeByte(END_CONFIRM_SESSION_FLAG);
                                dOut.writeByte(FAILED_SESSION_FLAG);
                                Log.d(TAG,"Node PowDev session failed at " + TimeAndDate.currentTime);
                            }
                        }
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
//                    mData.child("Server Socket Read and Reply Error").push().setValue(e.toString() + " " + TimeAndDate.currentTime);
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
            private boolean isNodePowDevObjectExisting(String MACAddr){
                for (NodePowDev nodePowDev : nodePowDevHashMap.values()){
                    if (nodePowDev.getMACAddr().equals(MACAddr)) {
                        return true;
                    }
                }
                return false;
            }
            private boolean isNodeSensorObjectExisting(String MACAddr){
                for (NodeSensor nodeSensor : nodeSensorHashMap.values()){
                    if (nodeSensor.getMACAddr().equals(MACAddr)) {
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



