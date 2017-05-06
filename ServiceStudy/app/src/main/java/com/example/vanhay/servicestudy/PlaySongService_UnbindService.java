package com.example.vanhay.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlaySongService_UnbindService extends Service {
    private static final String TAG = PlaySongService_UnbindService.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    public PlaySongService_UnbindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind() called!");
        // Return null because it won't called
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
            // Create instance play song
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.music_song);
        Log.d(TAG,"onCreate called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        Log.d(TAG,"onStartCommand called!");
        //      Để chứng minh Service không phải một Thread bạn hãy chạy đoạn code
        //      dưới đây và thử thao tác với giao diện xem có được không. Đồng thời theo dõi cửa sổ LogCat nhé!
      for (int i = 1; i < 10; i++) {
          // Tạm dừng 1s
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          Log.d(TAG, "Phương thức onStart() vẫn đang chạy " + i);
      }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        Log.d(TAG,"onDestroy() called!");
    }
}
