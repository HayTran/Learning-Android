package com.example.vanhay.servicestudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonUnbindActivity, buttonBindActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonBindActivity = (Button)findViewById(R.id.btnBindActivity);
        buttonUnbindActivity = (Button)findViewById(R.id.btnUnbindActivity);
        buttonBindActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BindService_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonUnbindActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UnbindService_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
