package com.example.haytran.subjectandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ImageButton btnChangeActivity, btnPlayPause,btnSound, btnStop;
    VideoView videoView;
    SeekBar seekBar;
    ListView listViewLastViewed;
    TextView textViewCurrentTime, textViewMaxTime, textViewVideoTitile,textViewLastViewed;
    ArrayAdapter arrayAdapter;
    ArrayList <MyVideo> arrayListLastViewed;
    Uri path = null;
    int currentPosition;
    String filename = "";
    int videoDuration = 0;
    boolean isPlaying = true;
    boolean isMuted = false;
    private Handler threadHandler = new Handler();
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
        addControls();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"On Destroy");
        Log.d(TAG,"Current Position:" + currentPosition);
        if (path != null) {
            MyVideo myVideo = new MyVideo(filename,videoDuration,currentPosition,path+"");
            dbHelper.insertOrUpdateVideo(myVideo);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"On Resume");
        Log.d(TAG,"Current Position:" + currentPosition);
        videoView.seekTo(currentPosition);
        videoView.start();
        textViewVideoTitile.setText(filename);
    }

    private void addControls() {
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (path != null) {
                    Intent  intent = new Intent(MainActivity.this,FullscreenActivity.class);
                    intent.putExtra("path",path+"");
                    intent.putExtra("currentPosition",videoView.getCurrentPosition());
                    intent.putExtra("filename",filename);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Not set video yet!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
                videoView.seekTo(0);
                isPlaying = !isPlaying;
            }
        });
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    videoView.pause();
                } else  {
                    btnPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
                    videoView.seekTo(videoView.getCurrentPosition());
                    videoView.start();
                }
                isPlaying = !isPlaying;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.pause();
                videoView.seekTo(seekBar.getProgress());
                videoView.start();
            }
        });
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMuted = !isMuted;
                if (isMuted) {
                    AudioManager audioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    try {
                        audioManager.adjustVolume(AudioManager.ADJUST_MUTE,0);
                    } catch (IllegalArgumentException ex) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                    }
                    btnSound.setImageResource(R.drawable.ic_volume_up_black_24dp);
                } else {
                    AudioManager audioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    try {
                        audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,0);
                    } catch (IllegalArgumentException ex) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                    }
                    btnSound.setImageResource(R.drawable.ic_volume_off_black_24dp);
                }
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoDuration = videoView.getDuration();
                seekBar.setMax(videoDuration);
                textViewMaxTime.setText(millisecondsToString(videoDuration));
            }
        });
        listViewLastViewed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     // Save history in SQLite
                if (path != null) {
                    MyVideo myVideo = new MyVideo(filename,videoDuration,currentPosition,path+"");
                    dbHelper.insertOrUpdateVideo(myVideo);
                }
                    // Starting update
                path = Uri.parse(arrayListLastViewed.get(position).getPath());
                videoView.setVideoURI(path);
                videoView.seekTo(arrayListLastViewed.get(position).getCurrentPositionMilisecond());
                videoView.start();
                filename = getFilename(path);
                textViewVideoTitile.setText(filename);
                MainActivity.UpdateSeekBarThread updateSeekBarThread = new MainActivity.UpdateSeekBarThread();
                threadHandler.postDelayed(updateSeekBarThread,50);
                    // Change Array List
                arrayListLastViewed.clear();
                arrayListLastViewed.addAll(dbHelper.getAllVideoLastViewed());
                arrayAdapter.notifyDataSetChanged();
            }
        });
        textViewLastViewed.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);

                b.setTitle("Question");
                b.setMessage("Are you sure you want to delete all history?");
                b.setPositiveButton("YES", new DialogInterface. OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dbHelper.deleteData();
                        arrayListLastViewed.clear();
                        arrayAdapter.notifyDataSetChanged();

                    }});
                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which)

                    {

                        dialog.cancel();

                    }

                });

                b.create().show();
                return false;
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
                // Get video info and start video
            videoView.setVideoURI(path);
            filename = getFilename(path);
            videoView.start();
            textViewVideoTitile.setText(filename);
                // Register for seekBar
            MainActivity.UpdateSeekBarThread updateSeekBarThread = new MainActivity.UpdateSeekBarThread();
            threadHandler.postDelayed(updateSeekBarThread,50);
        } else if (intent.getFlags() == 105){
                // Get video info and start video
            path = Uri.parse(intent.getStringExtra("path"));
            currentPosition = intent.getIntExtra("currentPosition",0);
            filename = intent.getStringExtra("filename");
            videoView.setVideoURI(path);
            videoView.seekTo(currentPosition);
            videoView.start();
            textViewVideoTitile.setText(filename);
            // Register for seekBar
            MainActivity.UpdateSeekBarThread updateSeekBarThread = new MainActivity.UpdateSeekBarThread();
            threadHandler.postDelayed(updateSeekBarThread,50);
        } else {
            Toast.makeText(this, "Do not have link to video", Toast.LENGTH_SHORT).show();
        }
            // Register for SQLite
        dbHelper = new DBHelper(MainActivity.this);
            // Initialize for list view
        arrayListLastViewed = new ArrayList<>();
        arrayListLastViewed.addAll(dbHelper.getAllVideoLastViewed());
        arrayAdapter = new LastViewedArrayAdapter(MainActivity.this,R.layout.layout_row,arrayListLastViewed);
        listViewLastViewed.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
    private void mapping() {
        videoView = (VideoView)findViewById(R.id.videoView);
        btnChangeActivity = (ImageButton)findViewById(R.id.btnChangeActivity);
        btnStop  = (ImageButton)findViewById(R.id.btnStop);
        btnPlayPause = (ImageButton)findViewById(R.id.btnPlayPause);
        btnSound = (ImageButton)findViewById(R.id.btnSound);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        listViewLastViewed = (ListView)findViewById(R.id.listViewLastViewed);
        textViewCurrentTime = (TextView)findViewById(R.id.textViewCurrentTime);
        textViewMaxTime = (TextView)findViewById(R.id.textViewMaxTime);
        textViewVideoTitile = (TextView)findViewById(R.id.textViewVideoTitle);
        textViewLastViewed = (TextView)findViewById(R.id.textViewLastViewed);
    }
    // Thread used to update for seekbar
    class UpdateSeekBarThread implements Runnable {
        public void run()  {
                // Get current postion to save in sqlite
            if (videoView.getCurrentPosition() > 0) {
                currentPosition = videoView.getCurrentPosition();
            }
            textViewCurrentTime.setText(millisecondsToString(videoView.getCurrentPosition()));
            seekBar.setProgress(videoView.getCurrentPosition());
            threadHandler.postDelayed(this, 50);
        }
    }
        // Convert milisecond to time
    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds - (minutes * 60000)) ;
        return minutes+":"+ seconds;
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
        // Get filename from path uri
    public String getFilename(Uri uri)
    {
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

