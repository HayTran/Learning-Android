package com.example.vanhay.inclass20170428;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<FlagItem> flagItemArrayList;
    ItemAdapter itemAdapter;
    ListView listView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listViewMainActivity);
        editText = (EditText)findViewById(R.id.editTextMainActivity);
        flagItemArrayList = new ArrayList<>();
        flagItemArrayList.add(new FlagItem("Australia",R.drawable.australia));
        flagItemArrayList.add(new FlagItem("Brazil",R.drawable.brazil));
        flagItemArrayList.add(new FlagItem("China",R.drawable.china));
        flagItemArrayList.add(new FlagItem("Europe",R.drawable.europe));
        flagItemArrayList.add(new FlagItem("Hungary",R.drawable.hungary));
        flagItemArrayList.add(new FlagItem("Indonesia",R.drawable.indonesia));
        flagItemArrayList.add(new FlagItem("Malaysia",R.drawable.malaysia));
        flagItemArrayList.add(new FlagItem("Mexico",R.drawable.mexico));
        itemAdapter = new ItemAdapter(MainActivity.this,R.layout.layout_row,flagItemArrayList);
        itemAdapter.notifyDataSetChanged();
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("SelectedObject",flagItemArrayList.get(position));
                intent.putExtra("Hay","Nhung");
                startActivity(intent);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    itemAdapter.getFilter().filter(s);
                    itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
