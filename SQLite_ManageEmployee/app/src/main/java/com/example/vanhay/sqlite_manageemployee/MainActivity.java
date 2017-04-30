package com.example.vanhay.sqlite_manageemployee;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Van Hay on 4/30/2017.
 */

public class MainActivity extends AppCompatActivity {
    ListView listView;
    DirectoryAdapter directoryAdapter;
    ArrayList <Directory> directoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
        addControl();

    }
    private void initDatabase(){
        // Intialize a database
        SQLite sqLite = new SQLite(MainActivity.this,"Hay_Nhung",null,1);
        // Create a table
        sqLite.queryWithoutReturn("CREATE TABLE IF NOT EXISTS Directory(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR, Phone INTEGER, Note VARCHAR)");

        // Add data into table
        sqLite.queryWithoutReturn("INSERT INTO Directory VALUES(null, 'Hay', 01653754549, 'My phone')");
        sqLite.queryWithoutReturn("INSERT INTO Directory VALUES(null, 'Nhung', 01666511481, 'My girl friend phone')");
        sqLite.queryWithoutReturn("INSERT INTO Directory VALUES(null, 'Unknow', 0902678123, 'Future phone')");

        // Query test
        Cursor cursor;
        cursor = sqLite.queryWithReturn("SELECT * FROM Directory");

        while (cursor.moveToNext()){
            directoryArrayList.add(new Directory(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3)));
        }
    }
    private void addControl() {
    }

    private void init() {
        directoryArrayList = new ArrayList<>();
        initDatabase();
        directoryAdapter = new DirectoryAdapter(MainActivity.this,R.layout.layout_row_directory_adapter,directoryArrayList);
        listView.setAdapter(directoryAdapter);
    }

    private void mapping() {
        listView = (ListView)findViewById(R.id.listViewMainActivity);
    }
}
