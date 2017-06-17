package com.a20170208.tranvanhay.appat.DashBoardFragment;

/**
 * Created by Van Hay on 02-Jun-17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.AlertMessage;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Van Hay on 02-Jun-17.
 */

public class AlertFragment extends Fragment {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    ListView listView;
    Button btnDeleteAlertDatabase, btnDeleteValueDatabase;
    ArrayList <AlertMessage> alertMessageArrayList;
    AlertArrayAdapter alertArrayAdapter;
    public AlertFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        init();
        addEvents();
    }

    private void addEvents() {
        btnDeleteAlertDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(FirebasePath.ALERT_DATABASE_PATH).setValue("0");
                getAlertDatabase();
            }
        });
        btnDeleteValueDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).setValue("0");
            }
        });
    }

    private void init() {
        alertMessageArrayList = new ArrayList<>();
        alertArrayAdapter = new AlertArrayAdapter(getContext(),R.layout.alert_row,alertMessageArrayList);
        listView.setAdapter(alertArrayAdapter);
        getAlertDatabase();
    }

    private void addControls(View view) {
        listView = (ListView)view.findViewById(R.id.listView);
        btnDeleteAlertDatabase = (Button)view.findViewById(R.id.btnDeleteAlertDatabase);
        btnDeleteValueDatabase = (Button)view.findViewById(R.id.btnDeleteValueDatabase);
    }

    private void getAlertDatabase(){
        // Get alert database
        mData.child(FirebasePath.ALERT_DATABASE_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alertMessageArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    alertMessageArrayList.add(new AlertMessage(
                            Long.valueOf(dataSnapshot1.getKey().toString()),
                            dataSnapshot1.getValue().toString()));
                }
                alertArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}