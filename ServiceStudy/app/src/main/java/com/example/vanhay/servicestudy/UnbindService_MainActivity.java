package com.example.vanhay.servicestudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UnbindService_MainActivity extends AppCompatActivity {
    Button btnStop, btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind_service_main);
        mapping();
        addControls();
    }

    private void addControls() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playSong();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSong();
            }
        });
    }

    private void mapping() {
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnStop = (Button)findViewById(R.id.btnStop);
    }

    private void playSong(){
        Intent intent = new Intent(UnbindService_MainActivity.this,PlaySongService_UnbindService.class);
        this.startService(intent);
    }
    private void stopSong(){
        Intent intent = new Intent(UnbindService_MainActivity.this,PlaySongService_UnbindService.class);
        this.stopService(intent);
    }

}
