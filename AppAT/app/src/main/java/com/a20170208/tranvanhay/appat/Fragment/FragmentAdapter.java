package com.a20170208.tranvanhay.appat.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Van Hay on 30-May-17.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    String  arrTitle [] = new String[] {"Sensor","PowDev","Menu"};
    public FragmentAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SensorFragment();
                break;
            case 1:
                fragment = new PowDevFragment();
                break;
            case 2:
                fragment = new MenuFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return arrTitle.length;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return arrTitle[position];
//    }
}
