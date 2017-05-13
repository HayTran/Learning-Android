package com.example.vanhay.googlemap_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = MapsActivity.class.getSimpleName();
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Marker yourCurrentLocationMarker;
    DatabaseReference mData;
    LatLng yourLatLng;
        // Declare variable for controls
    TextView textViewYourPosition;
    Button btnSignOut, btnSelectUser;
    Switch switchLookType;
    Spinner spinnerFocusSelection;
    ArrayAdapter arrayAdapter;
    ArrayList <String> arrayListSelection;
        // Declare for Firebase
    FirebaseUser mUser;
        // Manage selected user list
    ArrayList <UserPosition> selectedUserArrayList;
        // Declare variable for just focusing object the first time after finding
    boolean focusObject = false;
    boolean focusYourSelf = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MultiDex.install(this);
        mapping();
        init();
        addControls();
    }

    private void addControls() {
        /**
         *  Control setting
         */
        switchLookType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
        spinnerFocusSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String notification = "";
                if (position == 0) {
                    focusObject = false;
                    focusYourSelf = true;
                    notification = "Mỗi khi bạn di chuyển màn hình sẽ di chuyển theo bạn";
                } else if (position == 1) {
                    focusObject = true;
                    focusYourSelf = false;
                    notification = "Mỗi khi đối tượng di chuyển màn hình sẽ di chuyển theo đối tượng";
                } else if (position == 2) {
                    focusObject = false;
                    focusYourSelf = false;
                    notification = "Màn hình sẽ không di chuyển theo ai cả!";
                }
                Toast.makeText(MapsActivity.this, notification, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSelectedUserList();
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutFromApp();
            }
        });
    }

    private void mapping() {
        btnSignOut = (Button)findViewById(R.id.btnSignOut_MapsActivity);
        btnSelectUser = (Button)findViewById(R.id.btnSelectUser_MapsActivity);
        textViewYourPosition = (TextView) findViewById(R.id.textViewYourPositionMapsActivity);
        switchLookType = (Switch)findViewById(R.id.switchLookTypeMapsActivity);
        spinnerFocusSelection = (Spinner)findViewById(R.id.spinnerFocusSelection);
    }
    private void init() {
            // Set up for google map
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
            // Set up for firebase
        mData = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        switchLookType.setChecked(false);
            // Initialize for spinner
        arrayListSelection = new ArrayList<>();
        arrayListSelection.add("Tập trung vào chính bạn");
        arrayListSelection.add("Tập trung vào đối tượng");
        arrayListSelection.add("Không tập trung");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayListSelection);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerFocusSelection.setAdapter(arrayAdapter);
            // Get users
        getUsersWantToConnect();
        triggerUsersPosition();
    }
    /**
     * Trigger position people
     */
    private void triggerUsersPosition(){
        for (final UserPosition userPosition : selectedUserArrayList) {
            mData.child("Data").child(userPosition.getDisplayname()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double objectLatitude = 0, objectLongitude = 0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        if (dataSnapshot1.getKey().toString().equals("Latitude")) {
                            objectLatitude = Double.valueOf(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().toString().equals("Longitude")) {
                            objectLongitude = Double.valueOf(dataSnapshot1.getValue().toString());
                        }
                    }
                    findObjectLocation(objectLatitude,objectLongitude, userPosition);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
    private void getUsersWantToConnect(){
        Intent intent = getIntent();
        selectedUserArrayList =  (ArrayList) intent.getExtras().getParcelableArrayList("SelectedUserArrayList");
    }
    private void sendSelectedUserList(){
        Intent intent = new Intent(MapsActivity.this,SelectGroupActivity.class);
        intent.setFlags(2);
        intent.putParcelableArrayListExtra("SelectedUserArrayList",selectedUserArrayList);
        startActivity(intent);
    }
        // Get location from firebase when has changing
    private void findObjectLocation(double objectLatitude, double objectLongitude, UserPosition userPosition) {
        if (userPosition.getMarker() != null) {
            userPosition.getMarker().remove();
        }
        LatLng objectLatLng = new LatLng(objectLatitude,objectLongitude);
        Log.d(TAG,"Gotten Latitude: " + objectLatitude);
        Log.d(TAG,"Gotten Longitude: " + objectLongitude);
        Toast.makeText(this, "Lấy được vị trí của " + userPosition.getDisplayname(), Toast.LENGTH_SHORT).show();
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(objectLatLng);
        markerOptions1.title(userPosition.getDisplayname());
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        userPosition.setMarker(mGoogleMap.addMarker(markerOptions1));
        // Just focus the first time after specfying object
        if(focusObject) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(objectLatLng, 16));
        }
    }
    private void signOutFromApp(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
        alertDialog.setTitle("ĐĂNG XUẤT");
        alertDialog.setMessage("Bạn thực sự muốn đăng xuất khỏi ứng dụng?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MapsActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.create().show();
    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        Intent intent = new Intent(getBaseContext(),LocationService.class);
        this.startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getBaseContext(),LocationService.class);
        this.stopService(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this,android.
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
            // Place my home position
        LatLng myHomeLatLng = new LatLng(11.331826, 106.713425);
            // Move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHomeLatLng,16));
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
        if (yourCurrentLocationMarker != null) {
            yourCurrentLocationMarker.remove();
        }
        //Place current location marker
        yourLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mData.child("Data").child(mUser.getDisplayName()).child("Latitude").setValue(location.getLatitude());
        mData.child("Data").child(mUser.getDisplayName()).child("Longitude").setValue(location.getLongitude());
        mData.child("Data").child(mUser.getDisplayName()).child("TimeSend").setValue(TimeAndDate.currentTimeOffline);
        mData.child("User").child(mUser.getDisplayName()).setValue(TimeAndDate.currentTimeOffline);
        textViewYourPosition.setText(location.getLatitude() + ":" + location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(yourLatLng);
        markerOptions.title("Vị trí của bạn");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        yourCurrentLocationMarker = mGoogleMap.addMarker(markerOptions);
            // Just focus the first time after specfying object
        if(focusYourSelf){
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLatLng,16));
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Quyền truy cập vị trí")
                        .setMessage("Ứng dụng cần truy cập vị trí của bạn, vui lòng chấp nhân để ứng dụng có thể hoạt động")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Quyền truy cập vị trí bị từ chối", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
