package com.example.haytran.inclass20170417;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hay Tran on 17/04/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter {
    Context context;
    ArrayList <FlagItems> arrayList;
    int layoutResource;
    public CustomArrayAdapter(Context context, int resource, ArrayList<FlagItems> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.arrayList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutResource,null);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewLayoutRow);
        TextView textView = (TextView)convertView.findViewById(R.id.textViewLayoutRow);
        imageView.setImageResource(arrayList.get(position).getFlag());
        textView.setText(arrayList.get(position).getName());
        return convertView;
    }

}
