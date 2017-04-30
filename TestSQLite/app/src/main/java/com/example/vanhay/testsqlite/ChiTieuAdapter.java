package com.example.vanhay.testsqlite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Van Hay on 4/30/2017.
 */

public class ChiTieuAdapter extends ArrayAdapter {
    Context context;
    int layoutResource;
    ArrayList <ChiTieu> chiTieuArrayList;
    public ChiTieuAdapter(Context context, int resource, ArrayList <ChiTieu> objects ) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.chiTieuArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutResource,null);
        TextView textViewID = (TextView)convertView.findViewById(R.id.textViewID);
        TextView textViewTen = (TextView)convertView.findViewById(R.id.textViewTen);
        TextView textViewChiPhi = (TextView)convertView.findViewById(R.id.textViewChiPhi);
        TextView textViewGhiChu = (TextView)convertView.findViewById(R.id.textViewGhiChu);
        textViewID.setText(chiTieuArrayList.get(position).getId());
        textViewTen.setText(chiTieuArrayList.get(position).getTen());
        textViewChiPhi.setText(chiTieuArrayList.get(position).getChiPhi() + "");
        textViewGhiChu.setText(chiTieuArrayList.get(position).getGhiChu());
        return  convertView;
    }
}
