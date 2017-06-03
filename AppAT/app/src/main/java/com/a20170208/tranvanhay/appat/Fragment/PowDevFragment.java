package com.a20170208.tranvanhay.appat.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a20170208.tranvanhay.appat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Van Hay on 30-May-17.
 */

public class PowDevFragment extends Fragment {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = PowDevFragment.class.getSimpleName();
    PowDevArrayAdapter powDevArrayAdapter;
    ArrayList <NodePowDev> nodePowDevArrayList;
    ListView listView;
    public PowDevFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_powdev,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
        init();
        addEvent();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
            // Delete event listener when fragment stop
        mData.child("SocketServer").child("NodeDetails").child("NodePowDev").removeEventListener(detailsNodePowDevListener);
    }

    private void addControl(View view ) {
        listView = (ListView) view.findViewById(R.id.listView);
    }

    private void init() {
        nodePowDevArrayList = new ArrayList<>();
        powDevArrayAdapter = new PowDevArrayAdapter(getContext(),R.layout.powdev_row,nodePowDevArrayList);
        listView.setAdapter(powDevArrayAdapter);
    }
    private void addEvent() {
        mData.child("SocketServer").child("NodeList").child("NodePowDev").addListenerForSingleValueEvent(listNodePowDevListener);
    }

    private ValueEventListener listNodePowDevListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                NodePowDev nodePowDev = new NodePowDev();
                nodePowDev.setID(dataSnapshot1.getKey());
                nodePowDevArrayList.add(nodePowDev);
                    // Wait until get all list of node powdev
                nodePowDevArrayList.add(new NodePowDev("0f:d3","NodePowDevxxx",5,false,false,false,false,false,"11:40",false));
                mData.child("SocketServer").child("NodeDetails").child("NodePowDev").addValueEventListener(detailsNodePowDevListener);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener detailsNodePowDevListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                for (NodePowDev nodePowDev : nodePowDevArrayList) {
                    String ID = nodePowDev.getID();
                    if (dataSnapshot1.getKey().equals(ID)) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot2.getKey().equals("MACAddr")) {
                                nodePowDev.setMACAddr(dataSnapshot2.getValue().toString());
                            }   else if (dataSnapshot2.getKey().equals("strengthWifi")) {
                                nodePowDev.setStrengthWifi(Integer.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("dev0")) {
                                nodePowDev.setDev0(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("dev1")) {
                                nodePowDev.setDev1(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("buzzer")) {
                                nodePowDev.setBuzzer(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("sim0")) {
                                nodePowDev.setSim0(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("sim1")) {
                                nodePowDev.setSim1(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("timeOperation")) {
                                nodePowDev.setTimeOperation(dataSnapshot2.getValue().toString());
                            }

                        }
                        powDevArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        };
    private boolean stringToBoolean(String value){
        int convertedValue = Integer.valueOf(value);
        if (convertedValue > 0) {
            return false;
        } else {
            return true;
        }
            // Return false > 0 because 0 is active
    }
}
