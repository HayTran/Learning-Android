package com.example.vanhay.inclass20170424;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Van Hay on 4/24/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.ItemViewHolder>{
    Context context;
    int layoutResource;
    ArrayList <Item> itemArrayList;


    public ItemAdapter(Context context, ArrayList<Item> itemArrayList, int layoutResource) {
        this.context = context;
        this.itemArrayList = itemArrayList;
        this.layoutResource = layoutResource;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutResource,parent,false); // Must have using parent parameter
        return new  ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textView.setText(itemArrayList.get(position).getName());
        holder.imageView.setImageResource(itemArrayList.get(position).getFlag());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textViewLayoutRowWithCard);
            imageView = (ImageView)itemView.findViewById(R.id.imageViewLayoutRowWithCard);
        }
    }
}
