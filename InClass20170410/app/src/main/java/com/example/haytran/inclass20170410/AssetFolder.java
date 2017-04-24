package com.example.haytran.inclass20170410;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AssetFolder extends AppCompatActivity {
    TextView textView;
    ListView listView;
    ArrayList <String> arrayList;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_folder);
        addControl();
        init();
        addEvent();
    }

    private void addEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(arrayList.get(i).toString());
                textView.setTypeface(Typeface.createFromAsset(getAssets(),"font/"+arrayList.get(i)));
            }
        });
    }

    private void init() {
        // intialize for list view
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(AssetFolder.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        // intialize for assets folder
        AssetManager assetManager = getAssets();
        try {
            String [] arrFont = assetManager.list("font");
            arrayList.addAll(Arrays.asList(arrFont)); // convert normal array type to collection type.
            arrayAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addControl() {
        textView = (TextView)findViewById(R.id.textViewAssetFolderActivity);
        listView = (ListView)findViewById(R.id.listViewAssetFolderActivity);
    }
}
