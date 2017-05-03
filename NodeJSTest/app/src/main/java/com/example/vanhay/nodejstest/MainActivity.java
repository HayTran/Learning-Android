package com.example.vanhay.nodejstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    Button buttonSignIn;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://androidnodejs.herokuapp.com");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
        addControls();

    }

    private void addControls() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("client-gui-username", editTextUsername.getText().toString());
            }
        });
    }

    private void init() {
        Log.d("MainActivity","Is connected: " + mSocket.connected());
        mSocket.on("server-gui-ve",eventCallback);
        }
    private void mapping() {
        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        buttonSignIn = (Button)findViewById(R.id.btnSignIn);
    }
    private Emitter.Listener eventCallback = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final String message = args[0].toString();
            Log.d("message", message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
