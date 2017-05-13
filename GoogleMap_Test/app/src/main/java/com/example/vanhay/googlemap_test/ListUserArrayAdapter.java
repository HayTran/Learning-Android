package com.example.vanhay.googlemap_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Van Hay on 11-May-17.
 */

public class ListUserArrayAdapter extends ArrayAdapter {
    private static final String TAG = ListUserArrayAdapter.class.getSimpleName();
    Context context;
    int layoutResource;
    ArrayList <UserPosition> userPositionArrayList;
    public ListUserArrayAdapter(Context context, int resource, ArrayList<UserPosition> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.userPositionArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView,ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutResource,null);
        final CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkBox_LayoutRowListFriend);
        TextView textViewDisplayName = (TextView)convertView.findViewById(R.id.textViewDisplayName_LayoutRowListFriend);
        TextView textViewTimeSend = (TextView)convertView.findViewById(R.id.textViewTimeSend_LayoutRowListFriend);
        textViewDisplayName.setText(userPositionArrayList.get(position).getDisplayname());
        textViewTimeSend.setText(userPositionArrayList.get(position).getTimeSend());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG,"postion marked: " + position);
                if (checkBox.isChecked()){
                    userPositionArrayList.get(position).setMarked(1);
                } else {
                    userPositionArrayList.get(position).setMarked(0);
                }
            }
        });
        textViewDisplayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkBox.setChecked(!checkBox.isChecked());
            }
        });
        return convertView;
    }
}
