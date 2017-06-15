package com.a20170208.tranvanhay.appat.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.SensorNodeConfigActivity;
import com.a20170208.tranvanhay.appat.SensorValueDatabaseActivity;
import com.a20170208.tranvanhay.appat.UtilitiesClass.SensorNode;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Van Hay on 31-May-17.
 */

public class SensorArrayAdapter extends ArrayAdapter {
    private static final String TAG = SensorArrayAdapter.class.getSimpleName();
    AlertDialog.Builder builder;
    Context context;
    int layoutResource;
    ArrayList <SensorNode> sensorsArrayListNode;
    public SensorArrayAdapter(Context context, int resource, ArrayList <SensorNode> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.sensorsArrayListNode = objects;
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
        TextView textViewOption = (TextView)view.findViewById(R.id.textViewOption);
        TextView textViewStrengthWifi = (TextView)view.findViewById(R.id.textViewStrengthWifi);
        TextView textViewTemperature = (TextView)view.findViewById(R.id.textViewTemperature);
        TextView textViewConfigTemperature = (TextView)view.findViewById(R.id.textViewConfigTemperature);
        TextView textViewHumidity = (TextView)view.findViewById(R.id.textViewHumidity);
        TextView textViewConfigHumidity = (TextView)view.findViewById(R.id.textViewConfigHumidity);
        TextView textViewMeanFlameValue = (TextView)view.findViewById(R.id.textViewMeanFlameValue);
        TextView textViewConfigMeanFlameValue = (TextView)view.findViewById(R.id.textViewConfigMeanFlameValue);
        TextView textViewLightIntensity = (TextView)view.findViewById(R.id.textViewLightIntensity);
        TextView textViewConfigLightIntensity = (TextView)view.findViewById(R.id.textViewConfigLightIntensity);
        TextView textViewMQ2 = (TextView)view.findViewById(R.id.textViewMQ2);
        TextView textViewConfigMQ2 = (TextView)view.findViewById(R.id.textViewConfigMQ2);
        TextView textViewMQ7 = (TextView)view.findViewById(R.id.textViewMQ7);
        TextView textViewConfigMQ7 = (TextView)view.findViewById(R.id.textViewConfigMQ7);
        TextView textViewZone = (TextView)view.findViewById(R.id.textViewZone);
        TextView textViewTimeSend = (TextView)view.findViewById(R.id.textViewTimeSend);

            // Check Show when Layout row re-create
        checkShow(sensorsArrayListNode.get(position).getID(),linearLayoutContent);
        textViewID.setText(sensorsArrayListNode.get(position).getID());
        textViewStrengthWifi.setText(sensorsArrayListNode.get(position).getStrengthWifi()+ "");
        textViewTemperature.setText("Current: " + sensorsArrayListNode.get(position).getTemperature());
        textViewConfigTemperature.setText("Config: " + sensorsArrayListNode.get(position).getConfigTemperature() + "");
        textViewHumidity.setText("Current: " +sensorsArrayListNode.get(position).getHumidity() + "");
        textViewConfigHumidity.setText("Config: " +sensorsArrayListNode.get(position).getConfigHumidity()+ "");
        textViewMeanFlameValue.setText("Current: " +new DecimalFormat("##.##").format(sensorsArrayListNode.get(position).getMeanFlameValue()));
        textViewConfigMeanFlameValue.setText("Config: " +new DecimalFormat("##.##").format(sensorsArrayListNode.get(position).getConfigMeanFlameValue()));
        textViewLightIntensity.setText("Current: " +sensorsArrayListNode.get(position).getLightIntensity() + "");
        textViewConfigLightIntensity.setText("Config: " +sensorsArrayListNode.get(position).getConfigLightIntensity() +"");
        textViewMQ2.setText("Current: " +sensorsArrayListNode.get(position).getMQ2() + "");
        textViewConfigMQ2.setText("Config: " +sensorsArrayListNode.get(position).getConfigMQ2() +"");
        textViewMQ7.setText("Current: " +sensorsArrayListNode.get(position).getMQ7() + "");
        textViewConfigMQ7.setText("Config: " +sensorsArrayListNode.get(position).getConfigMQ7()+"");
        textViewZone.setText(sensorsArrayListNode.get(position).getZone()+"");
        textViewTimeSend.setText(sensorsArrayListNode.get(position).getTimeSend());
        // Toggle to show or hide detailed node
        linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = sensorsArrayListNode.get(position).getID();
                saveStateInSharePreference(ID, ! getStateInSharePreference(ID));
                checkShow(sensorsArrayListNode.get(position).getID(),linearLayoutContent);
            }
        });
        textViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(position);
            }
        });
        return view;
    }

    private void checkDialog(final int position){
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Option with " + sensorsArrayListNode.get(position).getID());
        builder.setMessage("See value database or set up value configuration");
        builder.setPositiveButton("See value database",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent intent = new Intent(context, SensorValueDatabaseActivity.class);
                        intent.putExtra("SensorNodeObject",sensorsArrayListNode.get(position));
                        context.startActivity(intent);
                    }
                });

        builder.setNeutralButton("Set up value configuration",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent intent = new Intent(context, SensorNodeConfigActivity.class);
                        intent.putExtra("SensorNodeObject",sensorsArrayListNode.get(position));
                        context.startActivity(intent);
                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        builder.show();
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
