package com.a20170208.tranvanhay.appat.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";
    DatabaseReference mData;
    TextView textViewTime,textViewFlame0,textViewFlame1,textViewHumidity,textViewTemperature;
    TextView textViewLightIntensity,textViewMQ2,textViewMQ7, textViewImageStatus;
    ImageView imageView;
        //    Bitmap bitmap;
    Button btnChangeToPingActivity, btnSignOut,btnCheckFCM;
    String pathImage = "";
    String atCurrent = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
//        addControl();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    private void init() {
//        // Define instance for firebase connection
//        mData = FirebaseDatabase.getInstance().getReference();
//    }
//    private void addControl() {
//        showInfoFromFirebase();
//        checkImageStorage();
//       btnSignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Log.d(TAG,"Display Activity Change To Sign In Activity");
//                Intent intent = new Intent(DisplayActivity.this,SignInActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnChangeToPingActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG,"Display Activity Change To Ping Activity");
//                Intent intent = new Intent(DisplayActivity.this,PingActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnCheckFCM.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new PublicIP(DisplayActivity.this).execute();
//            }
//        });
//    }
//    private void showInfoFromFirebase() {
//        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child("NodeSensor0").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    if (dataSnapshot1.getKey().equals("MQ2")) {
//                        textViewMQ2.setText(dataSnapshot1.getValue().toString());
//                    }
//                    if (dataSnapshot1.getKey().equals("MQ7")) {
//                        textViewMQ7.setText(dataSnapshot1.getValue().toString());
//                    }
//                    if (dataSnapshot1.getKey().equals("flame0")) {
//                        textViewFlame0.setText(dataSnapshot1.getValue().toString());
//                    }
//                    if (dataSnapshot1.getKey().equals("flame1")) {
//                        textViewFlame1.setText(dataSnapshot1.getValue().toString());
//                    }
//                    if (dataSnapshot1.getKey().equals("temperature")) {
//                        textViewTemperature.setText(dataSnapshot1.getValue().toString());
//                    }
//                    if (dataSnapshot1.getKey().equals("humidity")) {
//                        textViewHumidity.setText(dataSnapshot1.getValue().toString());
//                    }
//                    if (dataSnapshot1.getKey().equals("lightIntensity")) {
//                        textViewLightIntensity.setText(dataSnapshot1.getValue().toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void checkImageStorage() {
//        mData.child("Storage Image").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                textViewImageStatus.setText("Checked in Firebase at: " + atCurrent.toString());
//                pathImage = dataSnapshot.getValue().toString();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        new LoadImage().execute(pathImage);
//                    }
//                });
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private class LoadImage extends AsyncTask<String, Integer, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            Log.d(TAG,"Loading image from Firebase");
//            try {
//                URL url = new URL(strings[0]);
//                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                //Toast.makeText(SignInActivity.this, "URL= " + strings[0], Toast.LENGTH_SHORT).show();
//                return bitmap;
//            } catch (MalformedURLException e) {
//                Log.d("LoadImage", "MaformedURL " + e.getMessage());
//            } catch (IOException e) {
//                Log.d("LoadImage", "IOException " + e.getMessage());
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            imageView.setImageBitmap(bitmap);
//            Log.d(TAG,"Loading image from Firebase finished!");
//            //  Toast.makeText(SignInActivity.this, "Load finish", Toast.LENGTH_SHORT).show();
//        }
//    }
}