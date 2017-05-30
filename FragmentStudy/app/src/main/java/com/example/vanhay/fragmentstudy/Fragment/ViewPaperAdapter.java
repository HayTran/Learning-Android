package com.example.vanhay.fragmentstudy.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Van Hay on 29-May-17.
 */

public class ViewPaperAdapter extends FragmentStatePagerAdapter{
    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0 :
                fragment = new AndroidFragment();
                break;
            case 1:
                fragment = new IOSFragment();
                break;
            case 2:
                fragment = new NodeJSFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position){
//            case 0:
//                title = "Android";
//                break;
//            case 1:
//                title = "IOS";
//                break;
//            case 2:
//                title = "NodeJSpppp";
//                break;
//        }
//        return title;
//    }


}
