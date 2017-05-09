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

/**
 * Created by Tran Van Hay on 3/3/2017.
 */

public class SocketServerThread extends Thread {
    private static final String TAG = SocketServerThread.class.getSimpleName();
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    static final int SocketServerPORT = 8080;
    int count = 0;
    ServerSocket serverSocket;
    SocketServerThread (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    private int strengthWifi = 0;
    private int temperature = 0, humidity = 0;
    private int lightIntensity0 = 0, lightIntensity1 = 0;
    private int flameValue0_0 = 0, flameValue0_1 = 0,flameValue1_0 = 0, flameValue1_1 = 0;
    private int flameValue2_0 = 0, flameValue2_1 = 0,flameValue3_0 = 0, flameValue3_1 = 0;
    private int mq2Value0 = 0, mq2Value1 = 0, mq7Value0 = 0, mq7Value1 = 0;
    private double flameValue0 = 0, flameValue1 = 0, flameValue2 = 0, flameValue3 = 0, lightIntensity = 0, mq2Value = 0, mq7Value = 0;
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
        ARPNetwork arpNetwork;
        private Socket hostThreadSocket;  //this object specify whether this socket of which host
        int cnt;
        SocketServerReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }
        @Override
        public void run() {
            Log.d(TAG,"======================================================== Count = " + cnt);
                // Create a message to Client's socket
            DataOutputStream dOut = null;
            DataInputStream dIn = null;
            try {
                dIn = new DataInputStream(hostThreadSocket.getInputStream());
                    // Read data which sent from client
                temperature = dIn.readUnsignedByte();
                humidity = dIn.readUnsignedByte();
                flameValue0_0 = dIn.readUnsignedByte();
                flameValue0_1 = dIn.readUnsignedByte();
                flameValue1_0 = dIn.readUnsignedByte();
                flameValue1_1 = dIn.readUnsignedByte();
                flameValue2_0 = dIn.readUnsignedByte();
                flameValue2_1 = dIn.readUnsignedByte();
                flameValue3_0 = dIn.readUnsignedByte();
                flameValue3_1 = dIn.readUnsignedByte();
                lightIntensity0 = dIn.readUnsignedByte();
                lightIntensity1 = dIn.readUnsignedByte();
                mq2Value0 = dIn.readUnsignedByte();
                mq2Value1 = dIn.readUnsignedByte();
                mq7Value0 = dIn.readUnsignedByte();
                mq7Value1 = dIn.readUnsignedByte();
                strengthWifi = dIn.readUnsignedByte();
                    // Reply to client data already received.
                dOut = new DataOutputStream(hostThreadSocket.getOutputStream());
                dOut.writeByte(temperature);
                dOut.writeByte(humidity);
                dOut.writeByte(flameValue0_0);
                dOut.writeByte(flameValue0_1);
                dOut.writeByte(flameValue1_0);
                dOut.writeByte(flameValue1_1);
                dOut.writeByte(flameValue2_0);
                dOut.writeByte(flameValue2_1);
                dOut.writeByte(flameValue3_0);
                dOut.writeByte(flameValue3_1);
                dOut.writeByte(lightIntensity0);
                dOut.writeByte(lightIntensity1);
                dOut.writeByte(mq2Value0);
                dOut.writeByte(mq2Value1);
                dOut.writeByte(mq7Value0);
                dOut.writeByte(mq7Value1);
                dOut.writeByte(strengthWifi);
                    // Convert value
                convertValue();
                    // Send sensor data to NodeSensor
                sendDataToFirebase(new ARPNetwork(hostThreadSocket.getInetAddress().getHostAddress()).findMAC());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                mData.child("Server Socket Read and Reply Error").push().setValue(e.toString() +" "+ TimeAnDate.currentTimeOffline);
                e.printStackTrace();
                Log.d(TAG,"Exception Catched: "+ e.toString());
            } catch (NullPointerException e){
                e.printStackTrace();
            } finally {
                try {
                    dOut.close();
                    dIn.close();
                    hostThreadSocket.close();
                    Log.d(TAG,"Close Input, Output Stream, Socket");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG,"Exception Catched: "+ e.toString());
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void convertValue(){
        flameValue0 = flameValue0_0 + flameValue0_1*256;
        flameValue0 = 100 - (flameValue0/1024)*100;
        flameValue1 = flameValue1_0 + flameValue1_1*256;
        flameValue1 = 100 - (flameValue1/1024)*100;
        flameValue2 = flameValue2_0 + flameValue2_1*256;
        flameValue2 = 100 - (flameValue2/1024)*100;
        flameValue3 = flameValue3_0 + flameValue3_1*256;
        flameValue3 = 100 - (flameValue3/1024)*100;
        lightIntensity = lightIntensity0 + lightIntensity1*256;
        mq2Value = mq2Value0 + mq2Value1*256;
        mq7Value = mq7Value0 + mq7Value1*256;
    }
    public void sendDataToFirebase(String MACAddr){
        NodeSensor nodeSensor = new NodeSensor(strengthWifi,temperature,humidity,
                flameValue0,flameValue1,flameValue2,flameValue3,
                lightIntensity,mq2Value,mq7Value,MACAddr);

        mData.child("SocketServer").child(MACAddr).setValue(nodeSensor);
        Log.d(TAG,"Sent nodeSensor data to NodeSensor");
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



