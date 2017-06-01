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
        FragmentManager fm = getSupportFragmentManager();
        FragmentAdapter fa = new FragmentAdapter(fm, tabLayout);
        viewPager.setAdapter(fa);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_sensor_blue_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_fans_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_white_24dp);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_sensor_blue_24dp);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_fans_blue_24dp);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_blue_24dp);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_sensor_white_24dp);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_fans_white_24dp);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_white_24dp);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void addControl() {
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
    }
}
