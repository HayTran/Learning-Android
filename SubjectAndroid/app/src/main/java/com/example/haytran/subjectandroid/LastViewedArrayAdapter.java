package com.example.haytran.subjectandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Van Hay on 23-May-17.
 */

public class LastViewedArrayAdapter extends ArrayAdapter{
    Context context;
    ArrayList <MyVideo> myVideoArrayList;
    int layoutResource;
    public LastViewedArrayAdapter(Context context, int resource, ArrayList<MyVideo> myVideoArrayList) {
        super(context, resource, myVideoArrayList);
        this.context = context;
        this.layoutResource = resource;
        this.myVideoArrayList = myVideoArrayList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutResource,null);
        TextView textViewName = (TextView)convertView.findViewById(R.id.textViewName);
        TextView textViewDuration = (TextView)convertView.findViewById(R.id.textViewDuration);
        TextView textViewSeen = (TextView)convertView.findViewById(R.id.textViewSeen);
        textViewName.setText(myVideoArrayList.get(position).getName());
        textViewDuration.setText(myVideoArrayList.get(position).getDuration());
        textViewSeen.setText(myVideoArrayList.get(position).getCurrentPosition());
        return convertView;
    }

}
