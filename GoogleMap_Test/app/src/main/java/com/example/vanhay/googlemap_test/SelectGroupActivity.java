package com.example.vanhay.googlemap_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectGroupActivity extends AppCompatActivity {
    private static final String TAG = SelectGroupActivity.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    String yourDisplayName = "";
    Button btnConnectWith;
    ListView listViewShowUser;
    ArrayList <UserPosition> userArrayList;
    ArrayList <UserPosition> selectedUserArrayList;
    ListUserArrayAdapter listUserArrayAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        mapping();
        init();
        addControls();
    }

    private void addControls() {
        btnConnectWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSelectedUserList();
            }
        });
    }

    private void mapping() {
        btnConnectWith = (Button)findViewById(R.id.btnConnectWith_SelectGroupActivity);
        listViewShowUser = (ListView)findViewById(R.id.listViewUserOnline_SeclectGroupActivity);
    }

    private void init() {
        showProgressDialog();
        getSelectedUserList();
        // Send its time to firebase
        new TimeAndDate(mUser.getDisplayName()).showCurrentTime();
        userArrayList = new ArrayList<>();
        selectedUserArrayList = new ArrayList<>();
        // Set into listView
        listUserArrayAdapter = new ListUserArrayAdapter(SelectGroupActivity.this,
                R.layout.layout_row_listuser, userArrayList);
        listViewShowUser.setAdapter(listUserArrayAdapter);
        listUserArrayAdapter.notifyDataSetChanged();
        yourDisplayName = mUser.getDisplayName();
        loadOtherUsersInfo();
    }
    private void loadOtherUsersInfo(){
        mData.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    //Refresh users info
                if(userArrayList.size() > 0) {
                    userArrayList.clear();
                }
                    // Get Users Info from Database
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        // Do not get info of yourself
                    if (dataSnapshot1.getKey().toString().equals(yourDisplayName)) {
                        continue;
                    } else {
                        String displayname = dataSnapshot1.getKey().toString();
                        String timeSend = dataSnapshot1.getValue().toString();
                        userArrayList.add(new UserPosition(displayname, timeSend));
                    }
                }
                listUserArrayAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getSelectedUserList(){
        Intent intent = getIntent();
        if (intent.getFlags() == 2){
            selectedUserArrayList = intent.getParcelableArrayListExtra("SelectedUserArrayList");
        }
    }
    private void sendSelectedUserList(){
        selectedUserArrayList.clear();
        // Filter user whom selected
        for (UserPosition userList : userArrayList) {
            if (userList.getMarked() == 1) {
                selectedUserArrayList.add(userList);
            }
        }
        // Check and transmit to Maps Activity
        if (selectedUserArrayList.isEmpty()) {
            Toast.makeText(SelectGroupActivity.this, "Vui lòng chọn người muốn xem!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SelectGroupActivity.this,MapsActivity.class);
            intent.setFlags(1);
            intent.putParcelableArrayListExtra("SelectedUserArrayList", (ArrayList) selectedUserArrayList);
            startActivity(intent);
        }
    }
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang lấy dữ liệu, vui lòng chờ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
}
