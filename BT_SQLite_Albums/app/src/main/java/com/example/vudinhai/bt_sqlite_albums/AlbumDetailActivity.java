package com.example.vudinhai.bt_sqlite_albums;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlbumDetailActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<Songs> songsArrayList;

    ArrayAdapter arrayAdapter;

    DBHelper db;

    EditText edt;

    Songs songs;

    Albums albums;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        listView = (ListView)findViewById(R.id.list);

        db = new DBHelper(AlbumDetailActivity.this);

        albums = (Albums) getIntent().getSerializableExtra("Album");

        getSupportActionBar().setTitle(albums.getName());
        getSupportActionBar().setSubtitle("Total: " + db.getCount(albums.getId())+" Songs" );



        songsArrayList = db.getAllSongAlbums(albums.getId());

//        getSupportActionBar().setSubtitle(songsArrayList.size());
//
        arrayAdapter = new ArrayAdapter(AlbumDetailActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        songsArrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AlbumDetailActivity.this);
                builder.setTitle("Albums");
                builder.setMessage("Update or Delete");
                builder.setCancelable(false);


                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateSong(songsArrayList.get(position).getId());
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteSong(songsArrayList.get(position).getId());
                        arrayAdapter.clear();
                        arrayAdapter.addAll(db.getAllSongAlbums(albums.getId()));
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });


        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(AlbumDetailActivity.this);
                View view = layoutInflater.inflate(R.layout.layout_song,null);

                edt = (EditText) view.findViewById(R.id.editSong);

                AlertDialog.Builder builder = new AlertDialog.Builder(AlbumDetailActivity.this);

                builder.setTitle("Albums - Insert");
                builder.setView(view);
                builder.setCancelable(false);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.insertSongToAlbums(albums.getId(),edt.getText().toString());


                        arrayAdapter.clear();
                        arrayAdapter.addAll(db.getAllSongAlbums(albums.getId()));
                        arrayAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void updateSong(int id) {

         songs = db.getItemSong(id);

        LayoutInflater layoutInflater = LayoutInflater.from(AlbumDetailActivity.this);
        View view = layoutInflater.inflate(R.layout.layout_song,null);

        edt = (EditText) view.findViewById(R.id.editSong);
        edt.setText(songs.getName().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(AlbumDetailActivity.this);

        builder.setTitle("Albums - Update");
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db.updateSong(songs.getId(),edt.getText().toString());
                arrayAdapter.clear();
                arrayAdapter.addAll(db.getAllSongAlbums(albums.getId()));
                arrayAdapter.notifyDataSetChanged();


//                Toast.makeText(AlbumDetailActivity.this, songs.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.mnuAdd){
            LayoutInflater layoutInflater = LayoutInflater.from(AlbumDetailActivity.this);
            View view = layoutInflater.inflate(R.layout.layout_song,null);

            edt = (EditText) view.findViewById(R.id.editSong);


            AlertDialog.Builder builder = new AlertDialog.Builder(AlbumDetailActivity.this);

            builder.setTitle("Albums - Insert");
            builder.setView(view);
            builder.setCancelable(false);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    db.insertSongToAlbums(albums.getId(),edt.getText().toString());
                    arrayAdapter.clear();
                    arrayAdapter.addAll(db.getAllSongAlbums(albums.getId()));
                    arrayAdapter.notifyDataSetChanged();
                    getSupportActionBar().setSubtitle("Total: " + db.getCount(albums.getId())+" Songs" );
                    Toast.makeText(AlbumDetailActivity.this, edt.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }

        return super.onOptionsItemSelected(item);
    }


}
