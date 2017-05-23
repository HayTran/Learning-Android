package com.a20170208.tranvanhay.respberry3;

/**
 * Created by Van Hay on 23-May-17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

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
    String DATABASE_NAME = "Hay.sqlite";
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
                Toast.makeText(context, "Copy successfully!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Copy successfully!");
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
    ArrayList <String> getAllEmployee(){
        ArrayList <String> arrayList = new ArrayList<>();
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        String sql = "SELECT * FROM Relatives";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String tmp = cursor.getInt(0) +" " + cursor.getString(1) +" "+ cursor.getInt(2);
            arrayList.add(tmp);
            Log.d(TAG,"Line: " + tmp);
        }
        return arrayList;
    }
    void insertEmployee(){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",0);
        contentValues.put("name","Tai");
        contentValues.put("gender",1);
        if (db.insert("Relatives",null,contentValues) > 0 ){
            Toast.makeText(context, "Insert successfully!", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Insert successfully!");
        }
    }
}
