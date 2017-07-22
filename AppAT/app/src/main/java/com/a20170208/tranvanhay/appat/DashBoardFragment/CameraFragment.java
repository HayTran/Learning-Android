package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.a20170208.tranvanhay.appat.UtilitiesClass.TimeAndDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Van Hay on 04-Jul-17.
 */

public class CameraFragment extends Fragment {
    private static final String TAG = CameraFragment.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private  Handler mTakePicture = new Handler();
    TextView textViewTimeCapture, textViewStatus;
    Button btnCapture;
    ToggleButton toggleButtonWatchVideo;
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        init();
        addEvents();
    }

    private void addEvents() {
        mData.child(FirebasePath.IMAGE_TIME_CAPUTRE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long milisecond = Long.valueOf(dataSnapshot.getValue().toString());
                textViewTimeCapture.setText(TimeAndDate.convertMilisecondToTimeAndDate(milisecond));
                Log.d(TAG,"TimeCapture change");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(FirebasePath.CAMERA_STATUS_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textViewStatus.setText(dataSnapshot.getValue().toString());
                Log.d(TAG,"Status change");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(FirebasePath.IMAGE_LINK_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new LoadImage().execute(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(FirebasePath.CAMERA_CONTROL).setValue(System.currentTimeMillis());
            }
        });
        toggleButtonWatchVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTakePicture.post(runnableTakePicture);
                }   else {
                    mTakePicture.removeCallbacks(runnableTakePicture);
                }
            }
        });
    }
    private Runnable runnableTakePicture = new Runnable() {
        @Override
        public void run() {
            mData.child(FirebasePath.CAMERA_CONTROL).setValue(System.currentTimeMillis());
            Log.d(TAG,"Run runnableTakePicture");
            mTakePicture.postDelayed(runnableTakePicture,1000);
        }
    };
    private void init() {
        mData.child(FirebasePath.CAMERA_CONTROL).setValue(System.currentTimeMillis());
    }

    private void addControls(View view) {
        textViewTimeCapture = (TextView)view.findViewById(R.id.textViewTimeCapture);
        textViewStatus = (TextView)view.findViewById(R.id.textViewStatus);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        btnCapture = (Button)view.findViewById(R.id.btnCaptureImage);
        toggleButtonWatchVideo = (ToggleButton)view.findViewById(R.id.toggleButtonWatchVideo);
    }
    class LoadImage extends AsyncTask <String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String link = params[0];
            try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                return  bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            Log.d(TAG,"Loaded Image");
        }
    }
}
