package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Van Hay on 30-May-17.
 */

public class DashboardFragmentAdapter extends FragmentStatePagerAdapter {
    String  arrTitle [] = new String[] {"Sensor","PowDev","Alert","Camera","CustomMenu"};
    public DashboardFragmentAdapter(FragmentManager fm) {
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
                fragment = new AlertFragment();
                break;
            case 3:
                fragment = new CameraFragment();
                break;
            case 4:
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
