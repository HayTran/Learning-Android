package com.a20170208.tranvanhay.appat.Fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;

import java.util.ArrayList;

/**
 * Created by Van Hay on 02-Jun-17.
 */

public class MenuArrayAdapter extends ArrayAdapter {
    Context context;
    int layoutResource;
    ArrayList <CustomMenu> customMenuArrayList;

    public MenuArrayAdapter(Context context, int resource, ArrayList <CustomMenu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.customMenuArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.menu_row,null);
        ImageView imageViewMenuIcon = (ImageView)view.findViewById(R.id.imageViewMenuIcon);
        TextView textViewInfo = (TextView)view.findViewById(R.id.textViewInfo);

        imageViewMenuIcon.setImageResource(customMenuArrayList.get(position).getImage());
        textViewInfo.setText(customMenuArrayList.get(position).getName());
        return view;
    }
}
