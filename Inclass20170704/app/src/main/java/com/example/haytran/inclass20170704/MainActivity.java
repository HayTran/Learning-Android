package com.example.haytran.inclass20170704;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    TabHost tabHost;
    Button btnAdd, btnSub;
    EditText editTextNumA,editTextNumB;
    ListView listView;
    TextView textViewResult;
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
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("t1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("1 - Calculator");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("t2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("2 - History");
        tabHost.addTab(tabSpec);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int sum = calculator("+");
                textViewResult.setText(sum + "");
                if(arrayList.size() == 0) {
                    arrayList.add("a + b = " + sum );
                    arrayAdapter.notifyDataSetChanged();
                }
                else if (!arrayList.get(arrayList.size()-1).toString().equals("a + b = " + sum)){
                    arrayList.add("a + b = " + sum );
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = calculator("-");
                textViewResult.setText(sum + "");
                if(arrayList.size() == 0) {
                    arrayList.add("a - b = " + sum );
                    arrayAdapter.notifyDataSetChanged();
                }
                else if (!arrayList.get(arrayList.size()).toString().equals(sum+"")){
                    arrayList.add("a - b = " + sum );
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void addControl() {
        tabHost = (TabHost)findViewById(R.id.tabHost);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnSub = (Button)findViewById(R.id.btnSub);
        editTextNumA =  (EditText)findViewById(R.id.editTextNumberACalLayout);
        editTextNumB =  (EditText)findViewById(R.id.editTextNumberBCalLayout);
        listView = (ListView)findViewById(R.id.listViewHisLayout);
        textViewResult = (TextView)findViewById(R.id.textViewResult);
    }
    private int calculator(String sign){
        int a = 0;
        int b = 0;
        int sum = 0;
        a = Integer.valueOf(editTextNumA.getText().toString());
        b = Integer.valueOf(editTextNumB.getText().toString());
        if(sign.equals("+")){
            sum = a + b;
        }
        else {
            sum = a - b;
        }
        return  sum;
    }


}
