package com.a20170208.tranvanhay.appat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.SensorDatabaseFragment.SensorDatabaseFragmentAdapter;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.GraphAsyncTask;
import com.a20170208.tranvanhay.appat.UtilitiesClass.SensorNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;

import static android.content.ContentValues.TAG;

public class SensorValueDatabaseActivity extends AppCompatActivity {
    private static final String TAG = SensorValueDatabaseActivity.class.getSimpleName();
    TextView textViewID, textViewZone;
    SensorNode sensorNode;

    FragmentManager fm;
    SensorDatabaseFragmentAdapter fa;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_database);
        addCotrols();
        init();
        addEvents();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void addEvents() {

    }

    private void init() {
            // Back icon
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thống kê dữ liệu");
            // Get value
        Intent intent = getIntent();
        sensorNode = (SensorNode) intent.getSerializableExtra("SensorNodeObject");
        textViewID.setText(sensorNode.getID());
        textViewZone.setText(sensorNode.getZone()+"");
            // Set up fragment
        fm = getSupportFragmentManager();
        fa = new SensorDatabaseFragmentAdapter(fm, sensorNode.getID());
        viewPager.setAdapter(fa);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    private void addCotrols() {
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        textViewID = (TextView)findViewById(R.id.textViewID);
        textViewZone = (TextView)findViewById(R.id.textViewZone);
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
