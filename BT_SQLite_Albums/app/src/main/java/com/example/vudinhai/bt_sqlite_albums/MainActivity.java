package com.example.vudinhai.bt_sqlite_albums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    DBHelper db;

    AlbumsAdapter albumsAdapter;

    ArrayList<Albums> arrAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridView);

        db = new DBHelper(MainActivity.this);

        arrAlbums = db.getAllAlbums();

        albumsAdapter = new AlbumsAdapter(MainActivity.this,
                                           R.layout.layout_cell,
                                            arrAlbums);
        gridView.setAdapter(albumsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AlbumDetailActivity.class);

                intent.putExtra("Album",arrAlbums.get(position));

                startActivity(intent);
            }
        });
    }
}
