package com.example.vanhay.googlemap_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioButton radioButtonA, radioButtonB;
    Button buttonOK, buttonStepToNavigationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);
        mapping();
        init();
        addControls();
    }

    private void init() {
    }

    private void addControls() {

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!radioButtonA.isChecked() && !radioButtonB.isChecked()){
                    Toast.makeText(MainActivity.this, "Phải chọn bên trước!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                    if (radioButtonA.isChecked()){
                        intent.putExtra("Side","A");
                        startActivity(intent);
                    } else {
                        intent.putExtra("Side","B");
                        startActivity(intent);
                    }
                    finish();
                }
            }
        });
        buttonStepToNavigationDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void mapping() {
        radioButtonA = (RadioButton)findViewById(R.id.radioAMainActivity);
        radioButtonB = (RadioButton)findViewById(R.id.radioBMainActivity);
        buttonOK = (Button)findViewById(R.id.btnOKMainActivity);
        buttonStepToNavigationDrawable = (Button)findViewById(R.id.btnStepToNavigationDrawable);
    }
}
