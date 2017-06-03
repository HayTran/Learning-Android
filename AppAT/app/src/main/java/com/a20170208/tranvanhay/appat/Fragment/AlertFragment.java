package com.a20170208.tranvanhay.appat.Fragment;

/**
 * Created by Van Hay on 02-Jun-17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a20170208.tranvanhay.appat.R;

/**
 * Created by Van Hay on 02-Jun-17.
 */

public class AlertFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert,null);
    }
}