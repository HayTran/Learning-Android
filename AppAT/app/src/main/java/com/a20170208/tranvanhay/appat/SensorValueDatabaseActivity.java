package com.a20170208.tranvanhay.appat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.SensorNode;
import com.a20170208.tranvanhay.appat.UtilitiesClass.TimeAndDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SensorValueDatabaseActivity extends AppCompatActivity {
    private static final String TAG = SensorValueDatabaseActivity.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    TextView textViewID, textViewZone;
    TextView textViewTemperature, textViewHumidity, textViewMeanFlameValue, textViewLightIntensity, textViewMQ2, textViewMQ7;
    SensorNode sensorNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_database);
        addCotrols();
        init();
        addEvents();

    }

    private void addEvents() {
        Query query1 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ2").limitToFirst(1);
        Query query2 = mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).child(sensorNode.getID())
                .orderByChild("MQ2").limitToLast(1);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                Log.d(TAG,"Limit to first");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.d(TAG,"Key: " + TimeAndDate.convertMilisecondToTimeAndDate(Long.valueOf(dataSnapshot1.getKey().toString())));
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                       if (dataSnapshot2.getKey().equals("meanFlameValue")){
                           Log.d(TAG,"Value: " + dataSnapshot2.getValue());
                       }
                    }
                    count++;
                }
                Log.d(TAG,"Count = "  + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                Log.d(TAG,"limit to last");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.d(TAG,"Key: " + TimeAndDate.convertMilisecondToTimeAndDate(Long.valueOf(dataSnapshot1.getKey().toString())));
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        if (dataSnapshot2.getKey().equals("meanFlameValue")){
                            Log.d(TAG,"Value: " + dataSnapshot2.getValue());
                        }
                    }
                    count++;
                }
                Log.d(TAG,"Count = "  + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        // Back icon
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sensor value database");
        // Get value
        Intent intent = getIntent();
        sensorNode = (SensorNode) intent.getSerializableExtra("SensorNodeObject");
        textViewID.setText(sensorNode.getID());
        textViewZone.setText(sensorNode.getZone()+"");
    }

    private void addCotrols(){
        textViewID = (TextView)findViewById(R.id.textViewID);
        textViewZone = (TextView)findViewById(R.id.textViewZone);
        textViewTemperature = (TextView)findViewById(R.id.textViewTemperature);
        textViewHumidity = (TextView)findViewById(R.id.textViewHumidity);
        textViewMeanFlameValue = (TextView)findViewById(R.id.textViewMeanFlameValue);
        textViewLightIntensity = (TextView)findViewById(R.id.textViewLightIntensity);
        textViewMQ2 = (TextView)findViewById(R.id.textViewMQ2);
        textViewMQ7 = (TextView)findViewById(R.id.textViewMQ7);
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
