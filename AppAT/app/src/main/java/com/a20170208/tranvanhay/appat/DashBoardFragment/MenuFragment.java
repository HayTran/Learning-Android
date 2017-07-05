package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Van Hay on 30-May-17.
 */

public class MenuFragment extends Fragment {
    private static final String TAG = MenuFragment.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    Switch swAutoOperation;
    CheckBox chkMessageFCM, chkMessageGSM, chkCall;
    EditText editTextTimeSaveInDatabase;
    Button btnSave, btnCancel;
    public MenuFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
        init();
        addEvent();
    }

    private void addControl(View view) {
        swAutoOperation = (Switch)view.findViewById(R.id.swAutoOperation);
        chkMessageFCM = (CheckBox) view.findViewById(R.id.chkMessageFCM);
        chkMessageGSM = (CheckBox) view.findViewById(R.id.chkMessageGSM);
        chkCall = (CheckBox)view.findViewById(R.id.chkCall);
        editTextTimeSaveInDatabase = (EditText)view.findViewById(R.id.editTextTimeSaveInDatabase);
        btnSave = (Button)view.findViewById(R.id.btnSave);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
    }

    private void init() {
        mData.child(FirebasePath.CONTROLLER_AUTO_OPERATION_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                swAutoOperation.setChecked(Boolean.valueOf(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    boolean value = Boolean.valueOf(dataSnapshot1.getValue().toString());
                    if (dataSnapshot1.getKey().equals("InternetAlert")) {
                        chkMessageFCM.setChecked(value);
                    }   else if (dataSnapshot1.getKey().equals("SMSAlert")) {
                        chkMessageGSM.setChecked(value);
                    }   else if (dataSnapshot1.getKey().equals("CallAlert")) {
                        chkCall.setChecked(value);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mData.child(FirebasePath.TIME_SAVE_IN_DATABASE_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editTextTimeSaveInDatabase.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child(FirebasePath.CONTROLLER_AUTO_OPERATION_PATH).setValue(swAutoOperation.isChecked());
                mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).child("CallAlert").setValue(chkCall.isChecked());
                mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).child("SMSAlert").setValue(chkMessageGSM.isChecked());
                mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).child("InternetAlert").setValue(chkMessageFCM.isChecked());
                mData.child(FirebasePath.TIME_SAVE_IN_DATABASE_PATH).setValue(editTextTimeSaveInDatabase.getText().toString());
            }
        });
    }



}
