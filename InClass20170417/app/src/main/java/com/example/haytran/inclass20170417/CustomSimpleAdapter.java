package com.example.haytran.inclass20170417;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hay Tran on 17/04/2017.
 */

public class CustomSimpleAdapter extends SimpleAdapter{
    LayoutInflater layoutInflater;
    Context context;
    ArrayList <HashMap <String,String>> arrayList;
    public CustomSimpleAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.layoutInflater.from(context);
        this.context = context;
        this.arrayList = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position,convertView,parent);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageViewLayoutRow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, arrayList.get(position).get("name"), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
