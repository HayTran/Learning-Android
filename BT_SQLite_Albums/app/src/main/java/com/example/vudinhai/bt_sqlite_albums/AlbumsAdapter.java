package com.example.vudinhai.bt_sqlite_albums;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by vudinhai on 5/26/17.
 */

public class AlbumsAdapter extends ArrayAdapter<Albums> {

    Context context;
    int layout;
    ArrayList<Albums> arrayList;

    public AlbumsAdapter(Context context, int resource, ArrayList<Albums> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout,null);



        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageAlbums);

        ByteArrayInputStream imageStream = new ByteArrayInputStream(arrayList.get(position).getImage());
        Bitmap img = BitmapFactory.decodeStream(imageStream);

        imageView.setImageBitmap(img);



        TextView textView = (TextView)convertView.findViewById(R.id.detailAlbums);
        textView.setText(arrayList.get(position).getName() + "\n" );

        return convertView;
    }
}
