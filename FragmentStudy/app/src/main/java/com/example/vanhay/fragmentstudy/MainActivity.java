package com.example.vanhay.fragmentstudy;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vanhay.fragmentstudy.Fragment.ViewPaperAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        init();
    }

    private void init() {
        getSupportActionBar().hide();
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(fragmentManager);
        viewPager.setAdapter(viewPaperAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_alarm_on_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_backup_black_24dp);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_directions_run_black_24dp);
    }

    private void addControl() {
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
    }
}
