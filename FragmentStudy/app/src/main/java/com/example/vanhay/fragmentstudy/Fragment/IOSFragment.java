package com.example.vanhay.fragmentstudy.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.vanhay.fragmentstudy.R;

/**
 * Created by Van Hay on 29-May-17.
 */

public class IOSFragment extends android.support.v4.app.Fragment{
    private static final String TAG = IOSFragment.class.getSimpleName();
    public IOSFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.ios_fragment_layout,null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button)view.findViewById(R.id.btnHello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hello you", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
