package com.example.haytran.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent = new Intent(MainActivity.this, Main4Activity.class);
                startActivity(intent);
                break;
        }
    }
}

