package com.example.vanhay.testcamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    Timer timer = null;
    TimerTask timerTask = null;
    TextView textViewBeforeCompress, textViewAfterCompress;
    Bitmap decoded;
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
        timer.schedule(timerTask,0,200);
    }
    private void xulitaihinh() {
        String link ="http://42.117.253.238:4567/shot.jpg";
        imagetask task = new imagetask();
        task.execute(link);
    }

    private void addcontrol() {
        imgHinh= (ImageView) findViewById(R.id.imageShot);
        textViewBeforeCompress = (TextView) findViewById(R.id.textViewByteCountBeforeCompress);
        textViewAfterCompress = (TextView)findViewById(R.id.textViewByteCountAfterCompress);
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

            textViewBeforeCompress.setText("Byte: "+ bitmap.getByteCount()/1024);
            textViewAfterCompress.setText("Byte: " + decoded.getByteCount()/1024);
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
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                return  bitmap;

            }catch (Exception ex)
            {
                Log.e("LOI",ex.toString());
            }
            return null;
        }
    }
}

