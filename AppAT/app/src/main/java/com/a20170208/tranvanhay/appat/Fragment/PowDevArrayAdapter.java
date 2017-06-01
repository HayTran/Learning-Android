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
    ArrayList <NodePowDev> nodePowDevArrayList;
    public PowDevArrayAdapter(Context context, int resource, ArrayList <NodePowDev> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.nodePowDevArrayList = objects;
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
        TextView textViewStrengthWifi = (TextView)view.findViewById(R.id.textViewStrengthWifi);
        final Switch swDev0 = (Switch)view.findViewById(R.id.swDev0);
        final Switch swDev1 = (Switch)view.findViewById(R.id.swDev1);
        final Switch swBuzzer = (Switch)view.findViewById(R.id.swBuzzer);
        final Switch swSim0 = (Switch)view.findViewById(R.id.swSim0);
        final Switch swSim1 = (Switch)view.findViewById(R.id.swSim1);
        TextView textViewTimeOperation = (TextView)view.findViewById(R.id.textViewTimeOperation);

            // Check Show when Layout row re-create
        checkShow(nodePowDevArrayList.get(position).getID(),linearLayoutContent);
        textViewID.setText(nodePowDevArrayList.get(position).getID());
        textViewStrengthWifi.setText(nodePowDevArrayList.get(position).getStrengthWifi() + "");
        swDev0.setChecked(nodePowDevArrayList.get(position).isDev0());
        swDev1.setChecked(nodePowDevArrayList.get(position).isDev1());
        swBuzzer.setChecked(nodePowDevArrayList.get(position).isBuzzer());
        swSim0.setChecked(nodePowDevArrayList.get(position).isSim0());
        swSim1.setChecked(nodePowDevArrayList.get(position).isSim1());
        textViewTimeOperation.setText(nodePowDevArrayList.get(position).getTimeOperation());
            // Event listener for switches
        swDev0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(nodePowDevArrayList.get(position).getID(),"dev0",swDev0.isChecked());
            }
        });
        swDev1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(nodePowDevArrayList.get(position).getID(),"dev1",swDev1.isChecked());
            }
        });
        swBuzzer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(nodePowDevArrayList.get(position).getID(),"buzzer",swBuzzer.isChecked());
            }
        });
        swSim0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(nodePowDevArrayList.get(position).getID(),"sim0",swSim0.isChecked());
            }
        });
        swSim1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRequestToFirebase(nodePowDevArrayList.get(position).getID(),"sim1",swSim1.isChecked());
            }
        });
            // Toggle to show or hide detailed node
        linearLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = nodePowDevArrayList.get(position).getID();
                saveStateInSharePreference(ID, ! getStateInSharePreference(ID));
                checkShow(nodePowDevArrayList.get(position).getID(),linearLayoutContent);
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
        mData.child("SocketServer").child("NodeDetails").child("NodePowDev").child(ID).child(device).setValue(convertedValue);
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
