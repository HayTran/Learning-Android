package com.a20170208.tranvanhay.appat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.SensorNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class SensorNodeConfigActivity extends AppCompatActivity {
    TextView textViewID, textViewZone;
    TextView textViewTemperature, textViewHumidity, textViewMeanFlameValue, textViewLightIntensity, textViewMQ2, textViewMQ7;
    EditText editTextTemperature, editTextHumidity, editTextMeanFlameValue, editTextLightIntensity, editTextMQ2, editTextMQ7;
    Button btnOK, btnCancel;
    SensorNode sensorNode;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_node_config);
        addControls();
        init();
        addEvent();
    }

    private void addControls() {
        textViewID = (TextView)findViewById(R.id.textViewID);
        textViewZone = (TextView)findViewById(R.id.textViewZone);
        textViewTemperature = (TextView)findViewById(R.id.textViewTemperature);
        textViewHumidity = (TextView)findViewById(R.id.textViewHumidity);
        textViewMeanFlameValue = (TextView)findViewById(R.id.textViewMeanFlameValue);
        textViewLightIntensity = (TextView)findViewById(R.id.textViewLightIntensity);
        textViewMQ2 = (TextView)findViewById(R.id.textViewMQ2);
        textViewMQ7 = (TextView)findViewById(R.id.textViewMQ7);
        editTextTemperature = (EditText) findViewById(R.id.editTextTemperature);
        editTextHumidity = (EditText)findViewById(R.id.editTextHumidity);
        editTextMeanFlameValue = (EditText)findViewById(R.id.editTextMeanFlameValue);
        editTextLightIntensity = (EditText)findViewById(R.id.editTextLightIntensity);
        editTextMQ2 = (EditText)findViewById(R.id.editTextMQ2);
        editTextMQ7 = (EditText)findViewById(R.id.editTextMQ7);
        btnOK = (Button)findViewById(R.id.btnOK);
        btnCancel = (Button)findViewById(R.id.btnCancel);
    }
    private void init() {
            // Back icon
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cài đặt giá trị ngưỡng");
            // Get value
        Intent intent = getIntent();
        sensorNode = (SensorNode) intent.getSerializableExtra("SensorNodeObject");
        textViewID.setText(sensorNode.getID());
        textViewZone.setText(sensorNode.getZone()+"");
        textViewTemperature.setText(sensorNode.getTemperature()+" C");
        textViewHumidity.setText(sensorNode.getHumidity()+" %");
        textViewMeanFlameValue.setText(new DecimalFormat("##.##").format(sensorNode.getMeanFlameValue()) + " %");
        textViewLightIntensity.setText(sensorNode.getLightIntensity()+" lux");
        textViewMQ2.setText(sensorNode.getMQ2()+"");
        textViewMQ7.setText(sensorNode.getMQ7()+"");
        editTextTemperature.setText(sensorNode.getConfigTemperature()+"");
        editTextHumidity.setText(sensorNode.getConfigHumidity()+"");
        editTextMeanFlameValue.setText(new DecimalFormat("##.##").format(sensorNode.getConfigMeanFlameValue()));
        editTextLightIntensity.setText(sensorNode.getConfigLightIntensity()+"");
        editTextMQ2.setText(sensorNode.getConfigMQ2()+"");
        editTextMQ7.setText(sensorNode.getConfigMQ7()+"");
    }

    private void addEvent() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConfigInFirebase();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToDashboard();
            }
        });
    }

    private void setConfigInFirebase(){
        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH + sensorNode.getID())
                .child("temperature").setValue(editTextTemperature.getText().toString());
        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH + sensorNode.getID())
                .child("humidity").setValue(editTextHumidity.getText().toString());
        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH + sensorNode.getID())
                .child("meanFlameValue").setValue(editTextMeanFlameValue.getText().toString());
        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH + sensorNode.getID())
                .child("MQ2").setValue(editTextMQ2.getText().toString());
        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH + sensorNode.getID())
                .child("MQ7").setValue(editTextMQ7.getText().toString());
        mData.child(FirebasePath.SENSOR_VALUE_CONFIG_PATH + sensorNode.getID())
                .child("lightIntensity").setValue(editTextLightIntensity.getText().toString());
        backToDashboard();
    }
    private void backToDashboard(){
        Intent intent = new Intent(SensorNodeConfigActivity.this,DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
