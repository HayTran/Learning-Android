package com.example.vudinhai.bt_sqlite_albums;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by vudinhai on 5/26/17.
 */

public class DBHelper {
    String DATABASE_NAME = "AlbumDB.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;

    public DBHelper(Context context) {
        this.context = context;

        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copy sucessfull !!!", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = databaseInputStream.read(buffer)) > 0){
                databaseOutputStream.write(buffer,0,length);
            }


            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }


    public ArrayList<Albums> getAllAlbums(){
        ArrayList<Albums> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM Albums", null);

        while (cursor.moveToNext()){
            arr.add(new Albums(cursor.getInt(0),
                               cursor.getString(1),
                               cursor.getBlob(2),
                               cursor.getInt(3) ));
        }

        return  arr;
    }


    public int getCount(int id){
        String countQuery = "SELECT * FROM Playlist Where idAlbums = " + id;
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();


        return count;

    }

    public ArrayList<Songs> getAllSongAlbums(int idAlbums){
        ArrayList<Songs> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM Playlist Where idAlbums = " + idAlbums, null);

        while (cursor.moveToNext()){
            arr.add(new Songs(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)));
        }



        return  arr;
    }


    public void insertSongToAlbums(int idAlbum, String name){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("idAlbums",idAlbum);
        contentValues.put("name", name);

        if(db.insert("Playlist",null,contentValues) > 0){
            Toast.makeText(context, "Insert successful", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Insert not successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSong(int id){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        if(db.delete("Playlist","id=" + id, null) > 0){
            Toast.makeText(context, "delete successful", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateSong(int id, String name){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);

        db.update("Playlist",contentValues,"id = " + id,null);

    }

    public Songs getItemSong(int id){
        Songs song = new Songs();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        String sql = "SELECT * FROM Playlist WHERE id = " + id;

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

            song.setId(cursor.getInt(0));
            song.setName(cursor.getString(1));
            song.setIdAlbums(cursor.getInt(2));

        return song;
    }


    ArrayList<String> getAllEmployee(){
        ArrayList<String> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        String sql = "SELECT * FROM AlBum";

        Cursor cursor = db.rawQuery(sql,null);

        while(cursor.moveToNext()){
            String tmp = cursor.getInt(0) + cursor.getString(1) + cursor.getInt(2);
            arr.add(tmp);
        }

        cursor.close();

        return arr;
    }

    void insertStudent(int id, String name){

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);

//        db.insert("SinhVien",null,contentValues);

        if(db.insert("SinhVien",null,contentValues) > 0)
            Toast.makeText(context, "Save successfull", Toast.LENGTH_SHORT).show();
//        getAllSinhVien();
    }

    void updateStudent(int id, String name){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);

        db.update("SinhVien",contentValues,"id=?",new String[]{id+""});



    }

    void deleteStudent(int id){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        if(db.delete("SinhVien","id=" + id, null) > 0){
            Toast.makeText(context, "delete successful", Toast.LENGTH_SHORT).show();
        }

    }

    void insertEmployee(){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",10);
        contentValues.put("name", "Jeny");

        if(db.insert("Employee",null,contentValues) > 0){
            Toast.makeText(context, "Insert successful", Toast.LENGTH_SHORT).show();
        }
    }

}
