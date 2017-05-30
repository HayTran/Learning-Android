package com.a20170208.tranvanhay.appat.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a20170208.tranvanhay.appat.R;

/**
 * Created by Van Hay on 30-May-17.
 */

public class SensorFragment extends Fragment {
    public SensorFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor,null);
    }
}
