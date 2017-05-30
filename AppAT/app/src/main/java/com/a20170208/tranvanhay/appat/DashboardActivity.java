package com.a20170208.tranvanhay.appat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.a20170208.tranvanhay.appat.Fragment.FragmentAdapter;

public class DashboardActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addControl();
        init();
        addEvent();
    }

    private void addEvent() {

    }

    private void init() {
        getSupportActionBar().hide();
        FragmentManager fm = getSupportFragmentManager();
        FragmentAdapter fa = new FragmentAdapter(fm);
        viewPager.setAdapter(fa);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void addControl() {
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
    }
}
