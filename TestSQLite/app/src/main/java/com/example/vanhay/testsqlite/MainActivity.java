package com.example.vanhay.testsqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList <ChiTieu> chiTieuArrayList;
    ChiTieuAdapter chiTieuAdapter;
    SQLite db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
        addControl();

    }

    private void addControl() {

    }

    private void init() {
            // Constructor a database
        db = new SQLite(MainActivity.this,"QuanLy.SQLite",null,1);

            // Create table
        db.queryStatement("CREATE TABLE IF NOT EXISTS ChiTieu(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR, ChiPhi INTEGER, GhiChu VARCHAR)");

            // Add value
        db.queryStatement("INSERT INTO ChiTieu VALUES(null,'Mua điện thoại', 5000000,'Samsung')");
        db.queryStatement("INSERT INTO ChiTieu VALUES(null,'Mua tai nghe', 200000,'Apple')");
        db.queryStatement("INSERT INTO ChiTieu VALUES(null,'Ăn trưa', 30000,'Cơm sườn')");
        db.queryStatement("INSERT INTO ChiTieu VALUES(null,'Uống nước', 5000,'Nước  mía')");
            // Get data
        Cursor cursor = db.getData("SELECT * FROM ChiTieu");
        int count = 0;
        chiTieuArrayList = new ArrayList<>();
        while (cursor.moveToNext()){
            count++;
            chiTieuArrayList.add(new ChiTieu(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3)));
        }
        Toast.makeText(this, "Count = "+count, Toast.LENGTH_SHORT).show();
        chiTieuAdapter = new ChiTieuAdapter(MainActivity.this,R.layout.dong_chi_tieu_layout,chiTieuArrayList);
        listView.setAdapter(chiTieuAdapter);
    }

    private void mapping() {
        listView = (ListView)findViewById(R.id.listView);
    }
}
