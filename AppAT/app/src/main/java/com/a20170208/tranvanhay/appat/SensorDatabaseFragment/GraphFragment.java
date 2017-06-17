package com.a20170208.tranvanhay.appat.SensorDatabaseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;

/**
 * Created by Van Hay on 17-Jun-17.
 */

public class GraphFragment extends Fragment {
    private int position;

    public GraphFragment() {

    }

    public void setPosition(int position) {
        this.position = position;
    }

    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor_database,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView)view.findViewById(R.id.textView);
        textView.setText(this.position+"");
    }
}
