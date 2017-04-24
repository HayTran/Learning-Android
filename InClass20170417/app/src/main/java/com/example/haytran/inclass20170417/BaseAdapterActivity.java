package com.example.haytran.inclass20170417;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class BaseAdapterActivity extends AppCompatActivity {
    ListView listView;
    CustomBaseAdapter arrayAdapter;
    ArrayList<FlagItems> arrayList;
    ArrayAdapter arrayAdapterSpinner;
    ArrayList <String> arrayListSpinner;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);

        listView = (ListView)findViewById(R.id.listViewBaseAdapterActivity);
        spinner = (Spinner)findViewById(R.id.spinnerBaseActivity);

        arrayList = new ArrayList<>();
        arrayListSpinner = new ArrayList<>();
        arrayListSpinner.add("Cho Long 1");
        arrayListSpinner.add("Cho Long 2");
        arrayListSpinner.add("Cho Long 3");
        arrayListSpinner.add("Cho Long 4");

        arrayList.add(new FlagItems("US",R.drawable.united_states_of_america));
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
        arrayAdapter = new CustomBaseAdapter(BaseAdapterActivity.this,R.layout.layout_row,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(BaseAdapterActivity.this, arrayList.get(i).getName() + "--> OK", Toast.LENGTH_SHORT).show();
            }
        });
        arrayAdapterSpinner = new ArrayAdapter(BaseAdapterActivity.this,android.R.layout.simple_list_item_1,arrayListSpinner);
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapterSpinner);


    }
}
