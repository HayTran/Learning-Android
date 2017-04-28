package com.example.vanhay.inclass20170428;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Van Hay on 4/28/2017.
 */

public class ItemAdapter extends ArrayAdapter {
    ArrayList <FlagItem> flagItemsArrayList, temp, result; // temp, result used to contain temporary data
    Context context;
    int layoutResource;
    public ItemAdapter(Context context, int resource, ArrayList <FlagItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.flagItemsArrayList = objects;
        this.temp = new ArrayList<>(objects);
        this.result = new ArrayList<>(objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.layout_row,null);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewLayoutRow);
        TextView textView = (TextView)convertView.findViewById(R.id.textViewLayoutRow);
        imageView.setImageResource(flagItemsArrayList.get(position).getResource());
        textView.setText(flagItemsArrayList.get(position).getName());
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return myFilter;
    }
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                result.clear();
                for (FlagItem item : temp) {
                    if (item.getName().contains(constraint.toString().toLowerCase())){
                        result.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = result;
                filterResults.count = result.size();
                return filterResults;
            } else {
                return new FilterResults();
            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList <FlagItem> arrayList = (ArrayList<FlagItem>) results.values;
            if (results != null && results.count > 0) {
                clear();
                addAll(arrayList);
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
