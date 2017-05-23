package com.example.vanhay.sqlitestudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList <String> employees;
    ArrayAdapter arrayAdapter;
    DBHelper dbHelper;
    Button buttonInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(MainActivity.this);
        employees = new ArrayList<>(dbHelper.getAllEmployee());
        arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,employees);
        listView = (ListView)findViewById(R.id.listView);
        buttonInsert = (Button)findViewById(R.id.btnInsert);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Size Before:" + employees.size(), Toast.LENGTH_SHORT).show();
                dbHelper.insertEmployee();
                employees.clear();
                employees.addAll(dbHelper.getAllEmployee());
                Toast.makeText(MainActivity.this, "Size After:" + employees.size(), Toast.LENGTH_SHORT).show();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
