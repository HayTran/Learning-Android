package com.example.vanhay.inclass20170424;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleView_VActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Item> itemArrayList;
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view__v);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView_V);
        itemArrayList = new ArrayList<>();
        itemArrayList.add(new Item("Aruba",R.drawable.aruba));
        itemArrayList.add(new Item("Indonesia",R.drawable.indonesia));
        itemArrayList.add(new Item("China",R.drawable.china));
        itemArrayList.add(new Item("Brazil",R.drawable.brazil));
        itemArrayList.add(new Item("Japan",R.drawable.japan));
        itemArrayList.add(new Item("Israel",R.drawable.israel));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecycleView_VActivity.this,LinearLayoutManager.VERTICAL,false);

        itemAdapter = new ItemAdapter(RecycleView_VActivity.this,itemArrayList,R.layout.layout_row);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
