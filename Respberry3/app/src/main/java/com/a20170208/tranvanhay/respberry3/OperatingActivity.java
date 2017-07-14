package com.a20170208.tranvanhay.respberry3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.a20170208.tranvanhay.respberry3.UtilitiesClass.CameraRaspi;
import com.a20170208.tranvanhay.respberry3.UtilitiesClass.DBHelper;
import com.a20170208.tranvanhay.respberry3.UtilitiesClass.FCMServerThread;
import com.a20170208.tranvanhay.respberry3.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.respberry3.UtilitiesClass.SocketServerThread;
import com.a20170208.tranvanhay.respberry3.UtilitiesClass.TimeAndDate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;

/**
 * Created by Tran Van Hay on 4/22/2017.
 */

public class OperatingActivity extends Activity {
    private static final String TAG = OperatingActivity.class.getSimpleName();
    private static int TIME_TAKE_PICTURE = 10000;
        // Instance for Realtime Database
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        // Instance for creation a Server Socket
    protected ServerSocket serverSocket;
        // Instance for thread

        // Variable for display
    TextView txtSensor0,txtSensor1,txtSensor2,txtSensor3,txtSensor4,txtTime;
        // Instances for camera action
    private CameraRaspi mCamera;
    private Handler mCameraHandler;
    private HandlerThread mCameraThread;
    static SocketServerThread socketServerThread;
        // Instances for Storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    int count = 0;
    String linkURL = "";
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operating);
        mapping();
        new TimeAndDate().showCurrentTime();
        displayInMonitor();
        captureImage();
        triggerFCM();
        socketServerThread = new SocketServerThread(serverSocket);
        socketServerThread.start();
//        dbHelper = new DBHelper(OperatingActivity.this);
//        dbHelper.insertEmployee();
//        dbHelper.getAllEmployee();
    }

    private void mapping() {
        // mapping
        txtSensor0 = (TextView)findViewById(R.id.txtSensor0);
        txtSensor1 = (TextView)findViewById(R.id.txtSensor1);
        txtSensor2 = (TextView)findViewById(R.id.txtSensor2);
        txtSensor3 = (TextView)findViewById(R.id.txtSensor3);
        txtSensor4 = (TextView)findViewById(R.id.txtSensor4);
        txtTime = (TextView)findViewById(R.id.txtTime);
    }
    private void triggerFCM(){
        mData.child("Warning").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                String triggeredData =  dataSnapshot.getValue().toString();
                Log.d(TAG,"Triggered realtime database. Ref: Warning: "+ triggeredData);
                new FCMServerThread("Respberry 3",TimeAndDate.currentTime).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     * These below method serve for capture and send image to firebase
     */

    void captureImage(){
           mData.child(FirebasePath.CAMERA_CONTROL).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                    // IP's Webcam
                   String link ="http://192.168.0.200:8080/shot.jpg";
                   new GetImageTask().execute(link);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           }) ;
    }

    class GetImageTask extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
                // After gotten the image
            super.onPostExecute(bitmap);
                // Avoid doInBackGround return null
            if (bitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                upLoadImage(byteArray);
            } else {
                mData.child(FirebasePath.CAMERA_STATUS_PATH).setValue("Không có sẵn");
            }
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                String link = params[0];
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                return  bitmap;
            }catch (Exception ex)
            {
                Log.e("Has error",ex.toString());
            }
            return null;
        }
    }
    private void upLoadImage( byte[] data){
        count++;
        Log.d(TAG,"Uploading your image");
        StorageReference mountainsRef = storageRef.child("RaspberryCamera").child("image"+count+".jpg");
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG,"Upload Image failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                linkURL  = downloadUrl + "";
                mData.child(FirebasePath.IMAGE_LINK_PATH).setValue(linkURL);
                mData.child(FirebasePath.IMAGE_TIME_CAPUTRE_PATH).setValue(System.currentTimeMillis());
                mData.child(FirebasePath.CAMERA_STATUS_PATH).setValue("Đang có sẵn");
                Log.d(TAG,"Upload Image successfully ");
            }
        });
    }

    public void displayInMonitor(){
        // listen when Socket Server has change value then set new value
//        mData.child("SocketServer").child("Temperature").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                txtSensor0.setText(dataSnapshot.getValue().toString());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mData.child("SocketServer").child("Humidity").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                txtSensor1.setText(dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mData.child("SocketServer").child("Flame 0").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                txtSensor2.setText(dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mData.child("SocketServer").child("Flame 1").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                txtSensor3.setText(dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mData.child("SocketServer").child("Light Intensity").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                txtSensor4.setText(dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mData.child("At Current").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.shutDown();
        mCameraThread.quitSafely();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
