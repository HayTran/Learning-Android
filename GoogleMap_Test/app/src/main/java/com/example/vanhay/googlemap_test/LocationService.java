package com.example.vanhay.googlemap_test;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = LocationService.class.getSimpleName();
    DatabaseReference mData;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    public LocationService() {
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,android.
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        //Place current location marker
        UserPosition userPosition = new UserPosition(mUser.getDisplayName(),"online",0,
                location.getLatitude(),location.getLongitude(),
                TimeAndDate.currentTimeOffline);
        mData.child("Data").child(mUser.getDisplayName()).child("Latitude").setValue(location.getLatitude());
        mData.child("Data").child(mUser.getDisplayName()).child("Longitude").setValue(location.getLongitude());
        mData.child("Data").child(mUser.getDisplayName()).child("TimeSend").setValue(TimeAndDate.currentTimeOffline);
        mData.child("User").child(mUser.getDisplayName()).setValue(TimeAndDate.currentTimeOffline);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind() called");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mData = FirebaseDatabase.getInstance().getReference();
        buildGoogleApiClient();
        new TimeAndDate("Service").showCurrentTime();
        Log.d(TAG,"onCreate() called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        Log.d(TAG,"onDestroy called");
    }

}
