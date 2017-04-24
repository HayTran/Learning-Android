package com.example.vanhay.inclass20170424;

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
        Intent intent;
        switch (view.getId()){
            case (R.id.btnChangeToRecycleView_HActivity):
            intent = new Intent(MainActivity.this,RecycleView_HActivity.class);
            startActivity(intent);
            break;
            case (R.id.btnChangeToRecycleView_VActivity):
                intent = new Intent(MainActivity.this,RecycleView_VActivity.class);
                startActivity(intent);
                break;
            case (R.id.btnChangeToRecycleView_GridActivity):
                intent = new Intent(MainActivity.this,RecycleView_GridActivity.class);
                startActivity(intent);
                break;
            case (R.id.btnChangeToRecycleView_CardActivity):
                intent = new Intent(MainActivity.this,RecycleView_CardActivity.class);
                startActivity(intent);
                break;
        }

    }
}
