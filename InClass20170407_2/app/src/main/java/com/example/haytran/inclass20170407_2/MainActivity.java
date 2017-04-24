package com.example.haytran.inclass20170407_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    EditText editTextA,editTextB;
    Button btn;
    TextView txtResult;
    ListView listViewResult;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
        init();
    }
    private void init() {
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("T1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("1 - Calculator");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("T2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("2 - History");
        tabHost.addTab(tabSpec);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        listViewResult.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
    private void addEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator();
            }
        });
    }
    private void addControl() {
        editTextA = (EditText)findViewById(R.id.editTextNumberA);
        editTextB = (EditText)findViewById(R.id.editTextNumberB);
        btn = (Button)findViewById(R.id.btnCalculator1);
        txtResult = (TextView)findViewById(R.id.textViewResult);
        listViewResult = (ListView)findViewById(R.id.listViewTab2);
        tabHost = (TabHost)findViewById(R.id.tabHost);
    }
    private void calculator(){
        float a = 0;
        float b = 0;
        float result = 0;
        a = Float.valueOf(editTextA.getText().toString());
        b = Float.valueOf(editTextB.getText().toString());
        result = a + b;
        txtResult.setText("A + B = " + result);
        arrayList.add("A + B = " + result);
        arrayAdapter.notifyDataSetChanged();
    }
}
