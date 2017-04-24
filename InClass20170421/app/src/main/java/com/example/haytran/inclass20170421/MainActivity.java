package com.example.haytran.inclass20170421;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ViewPaperAdapter viewPaperAdapter;
    ViewPager viewPager;
    android.support.v7.widget.Toolbar toolBar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels) {
            //Landscape mode
        }
        else
        {
            viewPager = (ViewPager)findViewById(R.id.viewPager);
            toolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolBar);
            tabLayout = (TabLayout)findViewById(R.id.tabLayout);
            setSupportActionBar(toolBar);
            setUpViewPaper(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_assignment_ind_black_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_backup_black_24dp);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_backup_black_24dp);
        }

    }

    private void setUpViewPaper(ViewPager viewPager) {
        viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        viewPaperAdapter.addFragment(new Page1Fragment(),"One");
        viewPaperAdapter.addFragment(new Page2Fragment(),"Two");
        viewPaperAdapter.addFragment(new Page3Fragment(),"Three");
//        viewPaperAdapter.addFragment(new Page2Fragment(),"Four");
//        viewPaperAdapter.addFragment(new Page1Fragment(),"Five");
//        viewPaperAdapter.addFragment(new Page2Fragment(),"Six");
//        viewPaperAdapter.addFragment(new Page3Fragment(),"Seven");
//        viewPaperAdapter.addFragment(new Page3Fragment(),"Eight");
//        viewPaperAdapter.addFragment(new Page3Fragment(),"Nine");

        viewPager.setAdapter(viewPaperAdapter);
    }
}
