package com.example.vanhay.menuitem_inclass20170519;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList <String> arrayList;
    PopupMenu popupMenu;
    Button btnPopUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listViewManActivity);
        arrayList = new ArrayList<>();
        arrayList.add("Android");
        arrayList.add("iOS");
        arrayList.add("NodeJs");
        arrayList.add("Unity");
        arrayList.add("HTML_CSS");
        arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
            // Register for listview
        registerForContextMenu(listView);
        /**
         *  Popup menu
         */
        btnPopUp = (Button)findViewById(R.id.btnPopUp);
        popupMenu = new PopupMenu(MainActivity.this,btnPopUp);
        popupMenu.inflate(R.menu.menu);
        btnPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editMenu:
                Toast.makeText(this, "Edit Menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.saveMenu:
                Toast.makeText(this, "Save Menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.editMenuContext:
                item.setChecked(true);
                Toast.makeText(this, "Edit Context: " + adapterContextMenuInfo.position , Toast.LENGTH_SHORT).show();
                break;
            case R.id.saveMenuContext:
                item.setChecked(true);
                Toast.makeText(this, "Save Context:"+adapterContextMenuInfo.position, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

}
