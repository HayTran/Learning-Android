package com.example.vanhay.changeprefixphonenumber.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vanhay.changeprefixphonenumber.R;

/**
 * Created by Van Hay on 07-Jun-17.
 */

public class MainDirectoryFragment  extends Fragment {

    public MainDirectoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_directory_layout,null);
    }
}
