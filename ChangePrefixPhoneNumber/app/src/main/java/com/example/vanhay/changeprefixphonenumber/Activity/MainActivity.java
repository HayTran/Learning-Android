package com.example.vanhay.changeprefixphonenumber.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vanhay.changeprefixphonenumber.Fragment.FragmentAdapter;
import com.example.vanhay.changeprefixphonenumber.R;

public class MainActivity extends AppCompatActivity {
    FragmentAdapter fragmentAdapter;
    FragmentManager fragmentManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    View view0, view1, view2, view3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        init();
        addEvents();
    }



    private void addControls() {
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
    }

    private void init() {
            // Set up for actionbar
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.circle_flag_vietnam_128);
        getSupportActionBar().setTitle("WELCOME");
            // Set up for tablayout and viewpaper
        fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setFitsSystemWindows(true);
        view0 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
        view0.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_yellow_37dp);
        tabLayout.getTabAt(0).setCustomView(view0);

        view1 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_contacts_white_37dp);
        tabLayout.getTabAt(1).setCustomView(view1);

        view2 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edited_white_37dp);
        tabLayout.getTabAt(2).setCustomView(view2);

        view3 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_menu_white_37dp);
        tabLayout.getTabAt(3).setCustomView(view3);
        tabLayout.setSelected(true);
    }

    private void addEvents() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        view0.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_yellow_37dp);
                        tabLayout.getTabAt(0).setCustomView(view0);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                    case 1:
                        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_contacts_yellow_37dp);
                        tabLayout.getTabAt(1).setCustomView(view1);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                    case 2:
                        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edited_yellow_37dp);
                        tabLayout.getTabAt(2).setCustomView(view2);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                    case 3:
                        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_menu_yellow_37dp);
                        tabLayout.getTabAt(3).setCustomView(view3);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        view0.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_white_37dp);
                        tabLayout.getTabAt(0).setCustomView(view0);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                    case 1:
                        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_contacts_white_37dp);
                        tabLayout.getTabAt(1).setCustomView(view1);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                    case 2:
                        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edited_white_37dp);
                        tabLayout.getTabAt(2).setCustomView(view2);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                    case 3:
                        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_menu_white_37dp);
                        tabLayout.getTabAt(3).setCustomView(view3);
                        tabLayout.setSelectedTabIndicatorHeight(0);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
