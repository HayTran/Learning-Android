package com.a20170208.tranvanhay.appat.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Van Hay on 01-Jun-17.
 */

public class PowDevArrayAdapter extends ArrayAdapter {
    private static final String TAG = PowDevArrayAdapter.class.getSimpleName();
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    Context context;
    int layoutResource;
    ArrayList <PowDevNode> powDevNodeArrayList;
    public PowDevArrayAdapter(Context context, int resource, ArrayList <PowDevNode> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.powDevNodeArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG,"getView");
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(this.layoutResource,null);
        LinearLayout linearLayoutTitle = (LinearLayout)view.findViewById(R.id.linearLayoutTitle);
        final LinearLayout linearLayoutContent = (LinearLayout)view.findViewById(R.id.linearLayoutContent);
        TextView textViewID = (TextView)view.findViewById(R.id.textViewID);
        Switch swEnable = (Switch)view.findViewById(R.id.swEnable);
        TextView textViewStrengthWifi = (TextView)view.findViewById(R.id.textViewStrengthWifi);
        final Switch swDev0 = (Switch)view.findViewById(R.id.swDev0);
        final Switch swDev1 = (Switch)view.findViewById(R.id.swDev1);
        final Switch swBuzzer = (Switch)view.findViewById(R.id.swBuzzer);
        TextView textViewZone = (TextView)view.findViewById(R.id.textViewZone);
        TextView textViewTimeOperation = (TextView)view.findViewById(R.id.textViewTimeOperation);

            // Check Show when Layout row re-create
        checkShow(powDevNodeArrayList.get(position).getID(),linearLayoutContent);
        textViewID.setText(powDevNodeArrayList.get(position).getID());
        swEnable.setChecked(powDevNodeArrayList.get(position).isEnable());
        textViewStrengthWifi.setText(powDevNodeArrayList.get(position).getStrengthWifi() + "");
        swDev0.setChecked(powDevNodeArrayList.get(position).isDev0());
        swDev1.setChecked(powDevNodeArrayList.get(position).isDev1());
        swBuzzer.setChecked(powDevNodeArrayList.get(position).isBuzzer());
        textViewZone.setText(powDevNodeArrayList.get(position).getZone()+"");
        textViewTimeOperation.setText(powDevNodeArrayList.get(position).getTimeOperation());
            // Event listener for switches
        swEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                powDevNodeArrayList.get(position).setEnable(isChecked);
            }
        });
        if (swEnable.isChecked()) {
            swDev0.setEnabled(true);
            swDev1.setEnabled(true);
            swBuzzer.setEnabled(true);
        } else {
            swDev0.setEnabled(false);
            swDev1.setEnabled(false);
            swBuzzer.setEnabled(false);
        }
        swDev0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(powDevNodeArrayList.get(position).getID(),"dev0",swDev0.isChecked());
            }
        });
        swDev1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(powDevNodeArrayList.get(position).getID(),"dev1",swDev1.isChecked());
            }
        });
        swBuzzer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(powDevNodeArrayList.get(position).getID(),"buzzer",swBuzzer.isChecked());
            }
        });
            // Toggle to show or hide detailed node
        linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = powDevNodeArrayList.get(position).getID();
                saveStateInSharePreference(ID, ! getStateInSharePreference(ID));
                checkShow(powDevNodeArrayList.get(position).getID(),linearLayoutContent);
            }
        });
        return view;
    }
    private void sendRequestToFirebase(String ID, String device, boolean value){
        int convertedValue = 1;
        if (value == false) {
            convertedValue = 1;
        } else {
            convertedValue = 0;
        }
            // if value == false then convertedValue = 1 because 0 is active
        mData.child(FirebasePath.POWDEV_DETAILS_PATH).child(ID).child(device).setValue(convertedValue);
        Log.d(TAG,"send to Firebase");
    }
    private void checkShow(String ID, LinearLayout linearLayout){
        if (getStateInSharePreference(ID)) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }
    private void saveStateInSharePreference(String ID, boolean isShowed){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NodePowDevRowShow" + ID,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isShowed",isShowed);
        editor.commit();
        Log.d(TAG,"saveState");
    }
    private boolean getStateInSharePreference(String ID) {
        boolean isShowed = false;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NodePowDevRowShow" + ID,Context.MODE_PRIVATE);
        isShowed = sharedPreferences.getBoolean("isShowed",false);
        Log.d(TAG,"getState");
        return isShowed;
    }
}
