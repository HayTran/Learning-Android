package com.example.haytran.inclass20170421_2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Hay Tran on 21/04/2017.
 */

public class ViewPaperAdapter extends FragmentPagerAdapter {
    ArrayList <Fragment> fragmentArrayList = new ArrayList<>();
    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    public void addFragment(Fragment fragment){
        fragmentArrayList.add(fragment);
    }
}
