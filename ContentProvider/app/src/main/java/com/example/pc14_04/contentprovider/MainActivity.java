package com.example.pc14_04.contentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isPermissonGranted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyContactsPermissions(MainActivity.this);
    }

    public void onClickListener(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btnReadContact :
                intent = new Intent(this,ReadContact.class);
                break;
            case R.id.btnReadSMS:
                intent = new Intent(this,ReadSMS.class);
                break;
            case R.id.btnReadHistory:
                intent = new Intent(this,ReadHistory.class);
                break;
        }
        if (isPermissonGranted) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Quyền truy cập danh bạ không được cấp phép. Vui lòng tắt và mở lại ứng dụng", Toast.LENGTH_SHORT).show();
        }
        
    }
    // Storage Permissions variables
    private static final int REQUEST_ACCESS_CONTACTS_CODE = 1;
    private static String[] PERMISSIONS_CONTACTS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    public static void verifyContactsPermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_CONTACTS,
                    REQUEST_ACCESS_CONTACTS_CODE
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_CONTACTS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        isPermissonGranted = true;
                } else {
                    Toast.makeText(this, "Don't have permission. Please try again", Toast.LENGTH_SHORT).show();
                    isPermissonGranted = false;
                }
                return;
            }


        }
    }
}
