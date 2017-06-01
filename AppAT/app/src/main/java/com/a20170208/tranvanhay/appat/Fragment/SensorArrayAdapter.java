package com.a20170208.tranvanhay.appat.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;

import java.util.ArrayList;

/**
 * Created by Van Hay on 31-May-17.
 */

public class SensorArrayAdapter extends ArrayAdapter {
    private static final String TAG = SensorArrayAdapter.class.getSimpleName();
    Context context;
    int layoutResource;
    ArrayList <NodeSensor> nodeSensorsArrayList;
    public SensorArrayAdapter(Context context, int resource, ArrayList <NodeSensor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.nodeSensorsArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getView");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutResource,null);
        final LinearLayout linearLayoutContent = (LinearLayout)view.findViewById(R.id.linearLayoutContent);
        LinearLayout linearLayoutTitle = (LinearLayout)view.findViewById(R.id.linearLayoutTitle);
        TextView textViewID = (TextView)view.findViewById(R.id.textViewID);
        TextView textViewStrengthWifi = (TextView)view.findViewById(R.id.textViewStrengthWifi);
        TextView textViewTemperature = (TextView)view.findViewById(R.id.textViewTemperature);
        TextView textViewHumidity = (TextView)view.findViewById(R.id.textViewHumidity);
        TextView textViewFlame0 = (TextView)view.findViewById(R.id.textViewFlame0);
        TextView textViewFlame1 = (TextView)view.findViewById(R.id.textViewFlame1);
        TextView textViewFlame2 = (TextView)view.findViewById(R.id.textViewFlame2);
        TextView textViewFlame3 = (TextView)view.findViewById(R.id.textViewFlame3);
        TextView textViewLightIntensity = (TextView)view.findViewById(R.id.textViewLightIntensity);
        TextView textViewMQ2 = (TextView)view.findViewById(R.id.textViewMQ2);
        TextView textViewMQ7 = (TextView)view.findViewById(R.id.textViewMQ7);
        TextView textViewTimeSend = (TextView)view.findViewById(R.id.textViewTimeSend);

            // Check Show when Layout row re-create
        checkShow(nodeSensorsArrayList.get(position).getID(),linearLayoutContent);
        textViewID.setText(nodeSensorsArrayList.get(position).getID());
        textViewStrengthWifi.setText(nodeSensorsArrayList.get(position).getStrengthWifi()+ "");
        textViewTemperature.setText(nodeSensorsArrayList.get(position).getTemperature() + "");
        textViewHumidity.setText(nodeSensorsArrayList.get(position).getHumidity() + "");
        textViewFlame0.setText(nodeSensorsArrayList.get(position).getFlameValue0() + "");
        textViewFlame1.setText(nodeSensorsArrayList.get(position).getFlameValue1() + "");
        textViewFlame2.setText(nodeSensorsArrayList.get(position).getFlameValue2() + "");
        textViewFlame3.setText(nodeSensorsArrayList.get(position).getFlameValue3() + "");
        textViewLightIntensity.setText(nodeSensorsArrayList.get(position).getLightIntensity() + "");
        textViewMQ2.setText(nodeSensorsArrayList.get(position).getMQ2() + "");
        textViewMQ7.setText(nodeSensorsArrayList.get(position).getMQ7() + "");
        textViewTimeSend.setText(nodeSensorsArrayList.get(position).getTimeSend());
        // Toggle to show or hide detailed node
        linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = nodeSensorsArrayList.get(position).getID();
                saveStateInSharePreference(ID, ! getStateInSharePreference(ID));
                checkShow(nodeSensorsArrayList.get(position).getID(),linearLayoutContent);
            }
        });
        return view;
    }
    private void checkShow(String ID, LinearLayout linearLayout){
        if (getStateInSharePreference(ID)) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }
    private void saveStateInSharePreference(String ID, boolean isShowed){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NodeSensorRowShow" + ID,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isShowed",isShowed);
        editor.commit();
    }
    private boolean getStateInSharePreference(String ID) {
        boolean isShowed = false;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NodeSensorRowShow" + ID,Context.MODE_PRIVATE);
        isShowed = sharedPreferences.getBoolean("isShowed",false);
        return isShowed;
    }

}
