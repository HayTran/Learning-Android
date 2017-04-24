package com.example.haytran.inclass20170417;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleAdapterActivity extends AppCompatActivity {
    ListView listView;
    CustomSimpleAdapter customSimpleAdapter;
    String [] flagName = {"aruba","australia","brazil","bulgaria"};
    int [] flag = {R.drawable.aruba,
            R.drawable.australia,
            R.drawable.brazil,
            R.drawable.bulgaria};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);
        listView = (ListView)findViewById(R.id.listViewSimpleAdapterActivity);
        ArrayList <HashMap <String,String>> arrayList = new ArrayList<>();
        for (int i = 0; i < flagName.length; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name",flagName[i]);
            hashMap.put("image",flag[i]+"");
            arrayList.add(hashMap);
        }
        String [] from = {"name","image"};
        int [] to = {R.id.textViewLayoutRow,R.id.imageViewLayoutRow};
        customSimpleAdapter = new CustomSimpleAdapter(SimpleAdapterActivity.this,arrayList,R.layout.layout_row,from,to);
        listView.setAdapter(customSimpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SimpleAdapterActivity.this, flagName[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
