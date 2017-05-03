package com.a20170208.tranvanhay.respberry3;
import android.os.AsyncTask;
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

import static android.R.id.message;

/**
 * Created by Tran Van Hay on 3/3/2017.
 */

public class SocketServerThread extends AsyncTask <String,String, Integer> {
    private static final String TAG = SocketServerThread.class.getSimpleName();
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    static final int SocketServerPORT = 8080;
    int count = 0;
    ServerSocket serverSocket;
    SocketServerThread (ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    private static int temperature = 0, humidity = 0;
    private int flameValue0_0 = 0, flameValue0_1 = 0, lightIntensity0 = 0, lightIntensity1 = 0,flameValue1_0 = 0, flameValue1_1 = 0;
    private int mq2Value0 = 0, mq2Value1 = 0, mq7Value0 = 0, mq7Value1 = 0;
    private static double flameValue0 = 0, flameValue1 = 0, lightIntensity = 0, mq2Value = 0, mq7Value = 0;
    @Override
    protected Integer doInBackground(String... params) {
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
            mData.child("Server Socket Error").child("Receive").push().setValue(e.toString() + " " + TimeAnDate.currentTimeOffline);
            Log.d(TAG, "Exception Catched: " + e.toString());
            e.printStackTrace();
        }
        return 0;
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
                Log.d(TAG,"Before Data Input Stream in Receive Side");
                dIn = new DataInputStream(hostThreadSocket.getInputStream());
                Log.d(TAG,"After Data Input Stream in Receive Side");
                    // Read three value sent from ESP
                humidity = dIn.readUnsignedByte();
                temperature = dIn.readUnsignedByte();
                flameValue0_0 = dIn.readUnsignedByte();
                flameValue0_1 = dIn.readUnsignedByte();
                flameValue1_0 = dIn.readUnsignedByte();
                flameValue1_1 = dIn.readUnsignedByte();
                lightIntensity0 = dIn.readUnsignedByte();
                lightIntensity1 = dIn.readUnsignedByte();
                mq2Value0 = dIn.readUnsignedByte();
                mq2Value1 = dIn.readUnsignedByte();
                mq7Value0 = dIn.readUnsignedByte();
                mq7Value1 = dIn.readUnsignedByte();
                Log.d(TAG,"Read sensor value stream from client done");
                    // Convert value
                convertValue();
                Log.d(TAG,"Converting sensor value");
                    // Send sensor data to Firebase
                sendDataToFirebase(hostThreadSocket);
                Log.d(TAG,"Before Data Output Stream in Send Side");
                dOut = new DataOutputStream(hostThreadSocket.getOutputStream());
                dOut.writeByte(cnt);
                dOut.flush();
                Log.d(TAG,"After Data Output Stream in Send Side");
                Log.d(TAG,"MAC: " + new ARPNetwork(hostThreadSocket.getInetAddress().getHostAddress()).findMAC());

            } catch (IOException e) {
                    // TODO Auto-generated catch block
                mData.child("Server Socket Error").child("Send").push().setValue(e.toString() +" "+ TimeAnDate.currentTimeOffline);
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
                }
            }
        }
    }
    private void convertValue(){
        flameValue0 = flameValue0_0 + flameValue0_1*256;
        flameValue0 = 100 - (flameValue0/1024)*100;
        flameValue1 = flameValue1_0 + flameValue1_1*256;
        flameValue1 = 100 - (flameValue1/1024)*100;
        lightIntensity = lightIntensity0 + lightIntensity1*256;
        mq2Value = mq2Value0 + mq2Value1*256;
        mq7Value = mq7Value0 + mq7Value1*256;
    }
    public void sendDataToFirebase(Socket socket){
            // Send to Firebase
        mData.child("SocketServer").child("Socket IP").setValue("Soket Server: " + socket.getInetAddress());
        mData.child("SocketServer").child("Temperature").setValue(temperature+" Celius        ");
        mData.child("SocketServer").child("Humidity").setValue(humidity+" %      ");
        mData.child("SocketServer").child("Flame 0").setValue(flameValue0+" %        ");
        mData.child("SocketServer").child("Flame 1").setValue(flameValue1+" %         ");
        mData.child("SocketServer").child("MQ2").setValue(mq2Value+"           ");
        mData.child("SocketServer").child("MQ7").setValue(mq7Value+"           ");
        mData.child("SocketServer").child("Light Intensity").setValue(lightIntensity+" lux        ");
        mData.child("SocketServer").child("zMessage").setValue(message);
        Log.d(TAG,"Sent sensor data to Firebase");
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


