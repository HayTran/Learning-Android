package com.example.haytran.inclass20170421;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Hay Tran on 21/04/2017.
 */

public class ViewPaperAdapter extends FragmentPagerAdapter {
    ArrayList <Fragment> fragmentsArrayList = new ArrayList<>();
    ArrayList <String> fragmentTitle = new ArrayList<>();

    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsArrayList.size();
    }
    public void addFragment(Fragment fragment, String title){
        fragmentsArrayList.add(fragment);
        fragmentTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
