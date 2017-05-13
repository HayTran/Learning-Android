package com.example.haytran.subjectandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button btnChangeActivity;
    VideoView videoView;
    Uri path;
    int currentPosition = 0;
    String filename = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
        addControls();
    }

    private void addControls() {
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(MainActivity.this,FullscreenActivity.class);
                intent.putExtra("path",path+"");
                intent.putExtra("currentPosition",videoView.getCurrentPosition());
                intent.putExtra("filename",filename);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
            // Request Storage Permission
        verifyStoragePermissions(this);

            // Get info from intent
        Intent intent = getIntent();
            // Check whether video come from system or not
        if ((path = intent.getData()) != null) {
            videoView.setVideoURI(path);
            filename = getFilename(path);
            videoView.start();
        } else if (intent.getFlags() == 105){
            path = Uri.parse(intent.getStringExtra("path"));
            currentPosition = intent.getIntExtra("currentPosition",0);
            filename = intent.getStringExtra("filename");
            videoView.setVideoURI(path);
            videoView.seekTo(currentPosition);
            videoView.start();
        } else {
            Toast.makeText(this, "Chưa có đường dẫn đến video", Toast.LENGTH_SHORT).show();
        }
    }

    private void mapping() {
        btnChangeActivity = (Button)findViewById(R.id.btnChangeActivity_MainActivity);
        videoView = (VideoView)findViewById(R.id.videoView_MainActivity);
    }

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // Request read and write in storage method
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public String getFilename(Uri uri)
    {
        /*  Intent intent = getIntent();
            String name = intent.getData().getLastPathSegment();
            return name;*/
        String fileName = null;
        Context context=getApplicationContext();
        String scheme = uri.getScheme();
        if (scheme.equals("file")) {
            fileName = uri.getLastPathSegment();
        }
        else if (scheme.equals("content")) {
            String[] proj = { MediaStore.Video.Media.TITLE };
            Uri contentUri = null;
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null && cursor.getCount() != 0) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
                cursor.moveToFirst();
                fileName = cursor.getString(columnIndex);
            }
        }
        return fileName;
    }
}
