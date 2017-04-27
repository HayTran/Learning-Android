package com.example.vanhay.espsimulator;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends Activity {
    private static int TIME_SEND_TO_SERVER = 1000;
    Handler mHandler = new Handler();
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    TextView textViewMessage, textViewSeekBarValue;
    StringBuilder msg = new StringBuilder();
    SeekBar seekBar;
    ScrollView scrollView;
    private static int temperature = 33, humidity = 55;
    private int flameValue0_0 = 11, flameValue0_1 = 11, lightIntensity0 = 35, lightIntensity1 = 35,flameValue1_0 = 33, flameValue1_1 = 33;
    private int mq2Value0 = 11, mq2Value1 = 33, mq7Value0 = 44, mq7Value1 = 55;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(connectToServer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
        addControl();
    }

    private void addControl() {
        buttonClear.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                msg.delete(0,msg.length());
                textViewMessage.setText("");
            }});
        //Register a thread
        mHandler.post(connectToServer);
    }

    private void init() {
        msg.append("Send: ");
        seekBar.setMax(2000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBarValue.setText("Seek bar: " +progress);
                TIME_SEND_TO_SERVER = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    private void mapping() {
        // Mapped the variable in activity
        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonClear = (Button)findViewById(R.id.clear);
        textViewMessage = (TextView)findViewById(R.id.msg);
        textViewSeekBarValue = (TextView)findViewById(R.id.textViewSeerBarValue);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
    }

    // Do connect to server automactically
    private Runnable connectToServer = new Runnable() {
        @Override
        public void run() {
            // Begin connecting to server, intialize a object MyClientTask
            MyClientTask myClientTask = new MyClientTask(
                    editTextAddress.getText().toString(),
                    Integer.parseInt(editTextPort.getText().toString()));
            myClientTask.execute();
            mHandler.postDelayed(connectToServer,TIME_SEND_TO_SERVER);
        }
    };

    // My Client Task Class doing in AsynTask mode to get data from Server
    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;
        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }
        // Override the doInBackground AsynTask's method
        @Override
        protected Void doInBackground(Void... arg0) {
            Socket socket = null;
            try {

                // Intialize a socket with Address and Port belong to Server.
                socket = new Socket(dstAddress, dstPort);
                //Createa stream to send data to server
                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                dOut.writeByte(humidity);
                dOut.writeByte(temperature);
                dOut.writeByte(flameValue0_0);
                dOut.writeByte(flameValue0_1);
                dOut.writeByte(flameValue1_0);
                dOut.writeByte(flameValue1_1);
                dOut.writeByte(lightIntensity0);
                dOut.writeByte(lightIntensity1);
                dOut.writeByte(mq2Value0);
                dOut.writeByte(mq2Value1);
                dOut.writeByte(mq7Value0);
                dOut.writeByte(mq7Value1);
                msg.append("Sent finish!\n");
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewMessage.setText(msg.toString());
                    }
                });
                // Receive data sent from server
                SocketClientReceiveThread socketClientReceiveThread = new SocketClientReceiveThread(socket);
                socketClientReceiveThread.run();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                msg.append("UnknownHostException: " + e.toString()+"\n");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                msg.append("IOException: " + e.toString() +"\n");

            }finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
        // Override the doInBackground AsynTask's method
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
    private class SocketClientReceiveThread extends Thread {
        private Socket hostThreadSocket;  //this object specify whether this socket of which host
        SocketClientReceiveThread(Socket socket) {
            hostThreadSocket = socket;
        }
        @Override
        public void run() {
            // Create a message to Client's socket
            int countFromServer = 0;
            DataInputStream dIn = null;
            try {
                dIn = new DataInputStream(hostThreadSocket.getInputStream());
                countFromServer = dIn.read();
                msg.append("The number received from server: "+ countFromServer +"\n");
                // Updating UI must be implemented in UI Thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewMessage.setText(msg);
                    }
                });
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
               msg.append("Something wrong when receive reply from server " + e.toString() + "\n");
                // Updating UI must be implemented in UI Thread
            } finally {
                try {
                    dIn.close();
                    hostThreadSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Update into activity once again
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewMessage.setText(msg);
                }
            });
        }
    }
}