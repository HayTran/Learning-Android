package com.example.haytran.subjectandroid;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Van Hay on 22-May-17.
 */

public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    String DATABASE_NAME = "AndroidSubject.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;
    Context context;

    public DBHelper(Context context) {
        this.context = context;
        proceedSQLite();
    }

    private void proceedSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()){
            try {
                copyDatabaseFromAsset();
                Log.d(TAG,"Copy database from asset to databases folder successfully!");
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void copyDatabaseFromAsset(){
        try {
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);
            String outputString = getPathDatabaseSystem();
            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!file.exists()){
                file.mkdir();
            }
            OutputStream databaseOutputStream = new FileOutputStream(outputString);
            byte [] buffer = new byte[1024];
            int length;
            while((length = databaseInputStream.read(buffer)) > 0) {
                databaseOutputStream.write(buffer,0,length);
            }
            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    ArrayList <MyVideo> getAllVideoLastViewed(){
        ArrayList <MyVideo> arrayList = new ArrayList<>();
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        String sql = "SELECT * FROM LastViewed WHERE id > 0";          // > 0 because has 0 in database
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            MyVideo myVideo = new MyVideo(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4));
            arrayList.add(myVideo);
            Log.d(TAG,"Line:" + myVideo.toString());
        }
        return arrayList;
    }
    void insertOrUpdateVideo(MyVideo myVideo){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        String sqlCheck = "SELECT * FROM LastViewed WHERE path = " + "'" + myVideo.getPath() + "'";
        Cursor cursorCheck = db.rawQuery(sqlCheck,null);
        if (cursorCheck.getCount() > 0 ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("currentPosition",myVideo.getCurrentPositionMilisecond());
            if (db.update("LastViewed",contentValues,"path = " + "'" + myVideo.getPath() + "'",null) > 0){
                Log.d(TAG,"Update successfully!");
            }
            Log.d(TAG,"Record exist!");
        } else {
            String sqlGetMaxId = "SELECT * FROM LastViewed WHERE id = (SELECT MAX(id) FROM LastViewed)";
            Cursor cursor = db.rawQuery(sqlGetMaxId,null);
            cursor.moveToNext();
            int nextId = cursor.getInt(0) + 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put("id",nextId);
            contentValues.put("name",myVideo.getName());
            contentValues.put("duration",myVideo.getDurationMilisecond());
            contentValues.put("currentPosition",myVideo.getCurrentPositionMilisecond());
            contentValues.put("path",myVideo.getPath());
            if (db.insert("LastViewed",null,contentValues) > 0 ){
                Log.d(TAG,"Insert successfully!");
            }
        }
    }
    void deleteData(){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        String sql = "DELETE FROM LastViewed WHERE id >" + 0;
        db.execSQL(sql);
    }
}
