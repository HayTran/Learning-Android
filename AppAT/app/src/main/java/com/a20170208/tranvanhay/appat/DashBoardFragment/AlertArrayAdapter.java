package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.AlertMessage;

import java.util.ArrayList;

/**
 * Created by Van Hay on 14-Jun-17.
 */

public class AlertArrayAdapter extends ArrayAdapter {
    Context context;
    int layoutResource;
    ArrayList <AlertMessage> alertMessageArrayList;

    public AlertArrayAdapter(Context context, int resource, ArrayList <AlertMessage> alertMessageArrayList) {
        super(context, resource, alertMessageArrayList);
        this.context = context;
        this.layoutResource = resource;
        this.alertMessageArrayList = alertMessageArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(this.layoutResource,null);
        TextView textViewTimeSend = (TextView)convertView.findViewById(R.id.textViewTimeSend);
        TextView textViewBodyMessage = (TextView)convertView.findViewById(R.id.textViewBodyMessage);
            // Avoid index out of bound exception
        if (alertMessageArrayList.size() > 0) {
            textViewTimeSend.setText(alertMessageArrayList.get(position).getConvertedTimeSend());
            textViewBodyMessage.setText(alertMessageArrayList.get(position).getBodyMessage());
        } else {
            textViewBodyMessage.setText("Don't have any alert");
        }
        return convertView;
    }
}
