package com.a20170208.tranvanhay.appat.SensorDatabaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Van Hay on 17-Jun-17.
 */

public class SensorDatabaseFragmentAdapter extends FragmentStatePagerAdapter {
    String  arrTitle [] = new String[] {"Temperature","Humidity","Flame", "MQ2", "MQ7", "Light Intensity"};
    private String ID;
    public SensorDatabaseFragmentAdapter(FragmentManager fm, String ID) {
        super(fm);
        this.ID = ID;
    }

    @Override
    public Fragment getItem(int position) {
        GraphFragment graphFragment = new GraphFragment();
        graphFragment.setID(this.ID);
        graphFragment.setName(arrTitle[position]);
        return graphFragment;
    }

    @Override
    public int getCount() {
        return arrTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (arrTitle[position]) {
            case "Temperature" :
                return "Nhiệt độ";
            case "Humidity":
                return "Độ ẩm";
            case "Flame":
                return "Lửa";
            case "Light Intensity":
                return "Ánh sáng";
            case "MQ2":
                return "Khí Gas";
            case "MQ7":
                return "Khí CO";
        }
        return arrTitle[position];
    }
}
