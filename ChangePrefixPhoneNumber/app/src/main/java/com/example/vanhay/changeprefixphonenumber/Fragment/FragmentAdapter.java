package com.example.vanhay.changeprefixphonenumber.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Van Hay on 07-Jun-17.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    String [] arrFragmentTitle = new String[] {"Startup Action","Main Directory","Edited Directory","Menu"};

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: fragment = new StartupActionFragment();
                break;
            case 1: fragment = new MainDirectoryFragment();
                break;
            case 2: fragment = new EditedDirectoryFragment();
                break;
            case 3: fragment = new MenuFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return arrFragmentTitle.length;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return arrFragmentTitle[position];
//    }

}
