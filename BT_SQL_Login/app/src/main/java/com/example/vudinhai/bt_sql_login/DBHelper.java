package com.example.vudinhai.bt_sql_login;

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
 * Created by vudinhai on 5/27/17.
 */

public class DBHelper {

    String DATABASE_NAME = "UserDB.sqlite";
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


    public int checkAccount(String email, String password){
        int check = 0;

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        String sql = "SELECT * FROM users WHERE email = '" + email + "' and password = '" + password + "'";

        Cursor cursor = db.rawQuery(sql,null);

        check = cursor.getCount();

        return check;
    }

    public void insertAccount(String email, String password){

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);

        if(db.insert("users",null,contentValues) > 0){
            Toast.makeText(context, "Create Successfull", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Users> getAllUsers(){
        ArrayList<Users> arr = new ArrayList<>();

        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        String sql = "SELECT * FROM users";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            arr.add(new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
        }


        db.close();
        return  arr;
    }


    public void deleteAccount(int id){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        if(db.delete("users","id = " + id,null) > 0){
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        }
    }


    public Users getSingleAccount(int id){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        Users user = new Users();

        String sql = "SELECT * FROM users WHERE id = " + id;

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }

        user.setId(cursor.getInt(0));
        user.setEmail(cursor.getString(1));
        user.setPassword(cursor.getString(2));

        return user;
    }

    public void updateAccount(int id,String email, String password){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password", password);


        if (db.update("users",contentValues,"id = " + id, null) > 0)
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();


    }
}
