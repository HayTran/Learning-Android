package com.example.pc14_04.contentprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReadHistory extends AppCompatActivity {
    ListView listView;
    ArrayList<CustomHistory> customHistoryArrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_history);
        addControl();
        init();
    }

    private void init() {
        customHistoryArrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customHistoryArrayList);
        listView.setAdapter(arrayAdapter);
        showAllHistory();
    }

    private void showAllHistory() {
        Uri uri = CallLog.Calls.CONTENT_URI;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            int numberPosition = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            String number = cursor.getString(numberPosition);
            int typePosition = cursor.getColumnIndex(CallLog.Calls.TYPE);
            String type = cursor.getString(typePosition);
            int datePosition = cursor.getColumnIndex(CallLog.Calls.DATE);
            String date = cursor.getString(datePosition);
            customHistoryArrayList.add(new CustomHistory(number,type,date));
            Log.d("ReadHistory","Ok");
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void addControl() {
        listView = (ListView)findViewById(R.id.listView);
    }
}
