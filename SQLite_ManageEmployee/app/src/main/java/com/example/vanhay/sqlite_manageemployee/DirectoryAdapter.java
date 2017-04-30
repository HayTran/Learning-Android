package com.example.vanhay.sqlite_manageemployee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Van Hay on 4/30/2017.
 */

public class DirectoryAdapter extends ArrayAdapter {
    Context context;
    int layoutResource;
    ArrayList <Directory> directoryArrayList;
    public DirectoryAdapter(Context context, int resource, ArrayList<Directory> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.directoryArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutResource,null);
        TextView textViewID = (TextView)convertView.findViewById(R.id.textViewIDLayoutRow);
        TextView textViewName =  (TextView)convertView.findViewById(R.id.textViewNameLayoutRow);
        TextView textViewPhoneNumber = (TextView)convertView.findViewById(R.id.textViewPhoneNumberLayoutRow);
        TextView textViewNote = (TextView)convertView.findViewById(R.id.textViewNoteLayoutRow);
        Directory directory = directoryArrayList.get(position);

        textViewID.setText(directory.getID() + " ");
        textViewName.setText(directory.getName());
        textViewPhoneNumber.setText(directory.getPhoneNumber() + " ");
        textViewNote.setText(directory.getNote());

        Button buttonEdit = (Button)convertView.findViewById(R.id.btnEditLayoutRow);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is button edit listener", Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonDelete = (Button)convertView.findViewById(R.id.btnDeleteLayoutRow);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is button delete listener", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }
}
