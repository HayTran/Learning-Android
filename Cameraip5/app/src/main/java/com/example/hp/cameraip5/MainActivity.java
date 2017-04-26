package com.example.hp.cameraip5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    Timer timer = null;
    TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addcontrol();
        xulitaihinh();
        xulilap();
    }

    private void xulilap() {
        timerTask= new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xulitaihinh();
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,1);
    }

    private void xulitaihinh() {
        String link ="http://192.168.1.111:8080/shot.jpg";
        imagetask task = new imagetask();
        task.execute(link);
    }

    private void addcontrol() {
        imgHinh= (ImageView) findViewById(R.id.imageView);
    }
    class imagetask extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //kết quả sau khi tải xong thì ra cái hình

            super.onPostExecute(bitmap);

            imgHinh.setImageBitmap(bitmap);

            Toast.makeText(MainActivity.this, bitmap.getWidth()+"", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                String link = params[0];
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                return  bitmap;

            }catch (Exception ex)
            {
                Log.e("LOI",ex.toString());
            }

            return null;
        }
    }
}
