package com.example.haytran.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tabHost = (TabHost)findViewById(R.id.tabhost);

        tabHost.setup();

        /**
         * Standard tab
         */
//        TabHost.TabSpec tabSpec = tabHost.newTabSpec("t1"); //id for managament
//        tabSpec.setContent(R.id.tab1);
//        tabSpec.setIndicator("One");
//        tabHost.addTab(tabSpec);
//
//        tabSpec = tabHost.newTabSpec("t2");
//        tabSpec.setContent(R.id.tab2);
//        tabSpec.setIndicator("Two");
//        tabHost.addTab(tabSpec);
        /**
         * Customize tab
         */
        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.layout_indicator,null);
        ((TextView)tabIndicator.findViewById(R.id.textViewLayoutIndicator)).setText("One");
        // <=>
        // TextView txtView = tabIndicator.findViewById();
        // txtView.setText();
        ((ImageView)tabIndicator.findViewById(R.id.imgViewLayoutIndicator)).setImageResource(R.drawable.ic_alarm);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("t1"); //id for managament
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator(tabIndicator);
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTab(1);

        View tabIndicator1 = LayoutInflater.from(this).inflate(R.layout.layout_indicator,null);
        ((TextView)tabIndicator1.findViewById(R.id.textViewLayoutIndicator)).setText("Two");
        // <=>
        // TextView txtView = tabIndicator.findViewById();
        // txtView.setText();
        ((ImageView)tabIndicator1.findViewById(R.id.imgViewLayoutIndicator)).setImageResource(R.drawable.ic_brightness);
        tabSpec = tabHost.newTabSpec("t2"); //id for managament
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator(tabIndicator1);
        tabHost.addTab(tabSpec);

//        tabHost.setCurrentTab(0);
    }
}
