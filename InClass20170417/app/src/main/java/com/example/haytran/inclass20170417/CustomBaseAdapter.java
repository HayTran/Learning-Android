package com.example.haytran.inclass20170417;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by Hay Tran on 17/04/2017.
 */

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList <FlagItems> arrayList;
    int layoutResource;

    public CustomBaseAdapter(Context context, int layoutResource, ArrayList <FlagItems> object) {
        super();
        this.context = context;
        this.layoutResource = layoutResource;
        this.arrayList = object;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(layoutResource,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageViewLayoutRow);
        TextView textView = (TextView)view.findViewById(R.id.textViewLayoutRow);
        imageView.setImageResource(arrayList.get(i).getFlag());
        textView.setText(arrayList.get(i).getName());
        return view;
    }
}
