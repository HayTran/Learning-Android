package com.example.haytran.inclass20170417;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ArrayAdapterActivity extends AppCompatActivity {
    ListView listView;
    CustomArrayAdapter arrayAdapter;
    ArrayList <FlagItems> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);

        listView = (ListView)findViewById(R.id.listViewArrayAdapterActivity);
        arrayList = new ArrayList<>();

        arrayList.add(new FlagItems("australia",R.drawable.australia));
        arrayList.add(new FlagItems("brazil",R.drawable.brazil));
        arrayList.add(new FlagItems("bulgaria",R.drawable.bulgaria));
        arrayList.add(new FlagItems("canada",R.drawable.canada));
        arrayList.add(new FlagItems("china",R.drawable.china));
        arrayList.add(new FlagItems("croatia",R.drawable.croatia));
        arrayList.add(new FlagItems("czech",R.drawable.czech_republic));
        arrayList.add(new FlagItems("denmark",R.drawable.denmark));
        arrayList.add(new FlagItems("europe",R.drawable.europe));
        arrayList.add(new FlagItems("hungary",R.drawable.hungary));

        arrayAdapter = new CustomArrayAdapter (ArrayAdapterActivity.this,R.layout.layout_row,arrayList);
        listView.setAdapter(arrayAdapter);

    }
}
