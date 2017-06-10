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
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
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
    ArrayList <PowDevNode> powDevNodeArrayList;
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
        mData.child(FirebasePath.POWDEV_DETAILS_PATH).removeEventListener(detailsNodePowDevListener);
    }

    private void addControl(View view ) {
        listView = (ListView) view.findViewById(R.id.listView);
    }

    private void init() {
        powDevNodeArrayList = new ArrayList<>();
        powDevArrayAdapter = new PowDevArrayAdapter(getContext(),R.layout.powdev_row, powDevNodeArrayList);
        listView.setAdapter(powDevArrayAdapter);
    }
    private void addEvent() {
        mData.child(FirebasePath.POWDEV_LIST_PATH).addListenerForSingleValueEvent(listNodePowDevListener);
    }

    private ValueEventListener listNodePowDevListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                PowDevNode powDevNode = new PowDevNode();
                powDevNode.setID(dataSnapshot1.getKey());
                powDevNodeArrayList.add(powDevNode);
                    // Wait until get all list of node powdev
                powDevNodeArrayList.add(new PowDevNode("0f:d3","NodePowDevxxx",5,false,false,false,false,false,"11:40",true));
                mData.child(FirebasePath.POWDEV_DETAILS_PATH).addValueEventListener(detailsNodePowDevListener);
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
                for (PowDevNode powDevNode : powDevNodeArrayList) {
                    String ID = powDevNode.getID();
                    if (dataSnapshot1.getKey().equals(ID)) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot2.getKey().equals("MACAddr")) {
                                powDevNode.setMACAddr(dataSnapshot2.getValue().toString());
                            }   else if (dataSnapshot2.getKey().equals("zone")){
                                powDevNode.setZone(Integer.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("strengthWifi")) {
                                powDevNode.setStrengthWifi(Integer.valueOf(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("dev0")) {
                                powDevNode.setDev0(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("dev1")) {
                                powDevNode.setDev1(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("buzzer")) {
                                powDevNode.setBuzzer(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("sim0")) {
                                powDevNode.setSim0(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("sim1")) {
                                powDevNode.setSim1(stringToBoolean(dataSnapshot2.getValue().toString()));
                            }   else if (dataSnapshot2.getKey().equals("timeOperation")) {
                                powDevNode.setTimeOperation(dataSnapshot2.getValue().toString());
                            }   else if (dataSnapshot2.getKey().equals("isEnable")) {
                                powDevNode.setEnable(Boolean.valueOf(dataSnapshot2.getValue().toString()));
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
