package com.a20170208.tranvanhay.appat.DashBoardFragment;

/**
 * Created by Van Hay on 02-Jun-17.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    Button btnDeleteAlertDatabase;
    ArrayList <AlertMessage> alertMessageArrayList;
    AlertArrayAdapter alertArrayAdapter;
    AlertDialog.Builder builder;
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
                showDialog();
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
    private void showDialog(){
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nhắc nhở");
        builder.setMessage("Bạn có muốn xóa toàn bộ tin nhắn?");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        mData.child(FirebasePath.ALERT_DATABASE_PATH).setValue("0");
                        getAlertDatabase();
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton("Hủy",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
}