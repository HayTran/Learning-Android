package com.example.vanhay.changeprefixphonenumber;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vanhay.changeprefixphonenumber.Fragment.FragmentAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Just Test").push().setValue("OK");
    }



    private void addControls() {
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
    }

    private void init() {
            // Set up for actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.circle_flag_vietnam_128);
        getSupportActionBar().setTitle("WELCOME");
            // Set up for tablayout and viewpaper
        fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setFitsSystemWindows(true);
//        view1 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
//        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_yellow_37dp_37dp);
//        tabLayout.getTabAt(0).setCustomView(view0)


//        view1 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
//        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_contacts_white_37dp);
//        tabLayout.getTabAt(1).setCustomView(view1);
//
//        view2 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
//        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edited_white_37dp);
//        tabLayout.getTabAt(2).setCustomView(view2);
//
//        view3 = getLayoutInflater().inflate(R.layout.custom_tab_layout,null);
//        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_menu_white_37dp);
//        tabLayout.getTabAt(3).setCustomView(view3);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_yellow_37dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_contacts_white_37dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_edited_white_37dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_menu_white_37dp);

    }

    private void addEvents() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_yellow_37dp);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_contacts_yellow_37dp);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_edited_yellow_37dp);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_menu_yellow_37dp);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_white_37dp);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_contacts_white_37dp);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_edited_white_37dp);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_menu_white_37dp);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
