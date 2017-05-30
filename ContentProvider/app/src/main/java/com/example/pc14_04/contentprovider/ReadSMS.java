package com.example.pc14_04.contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import javax.xml.transform.URIResolver;

public class ReadSMS extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList <CustomSMS> customSMSArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);
        addControl();
        init();
        addEvent();
    }

    private void addEvent() {
    }

    private void init() {
        customSMSArrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(ReadSMS.this,android.R.layout.simple_list_item_1,customSMSArrayList);
        listView.setAdapter(arrayAdapter);
        showAllSMS();
    }

    private void showAllSMS() {
        Uri uri = Telephony.Sms.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        while (cursor.moveToNext()) {
            Log.d("ReadSMS","Alo");
            int positionNumber = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            String number = cursor.getString(positionNumber);
            int positionMessage = cursor.getColumnIndex(Telephony.Sms.BODY);
            String message = cursor.getString(positionMessage);
            customSMSArrayList.add(new CustomSMS(number,message));
            Log.d("ReadSMS","number: " + number + "\nbody: " + message);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void addControl() {
        listView = (ListView)findViewById(R.id.listView);
    }
}
