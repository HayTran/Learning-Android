package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.SignInActivity;
import com.a20170208.tranvanhay.appat.UtilitiesClass.FirebasePath;
import com.google.firebase.auth.FirebaseAuth;
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
    TextView textViewLastestTimeServerOperation;
    Button btnSave, btnCancel, btnDeleteValueDatabase, btnSignOut;
    AlertDialog.Builder builder;
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
        btnDeleteValueDatabase = (Button)view.findViewById(R.id.btnDeleteValueDatabase);
        btnSignOut = (Button)view.findViewById(R.id.btnSignOut);
        textViewLastestTimeServerOperation = (TextView)view.findViewById(R.id.textViewTimeServerOperation);
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
                int number = Integer.valueOf(dataSnapshot.getValue().toString()) / 1000;
                editTextTimeSaveInDatabase.setText(number+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child("At Current").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textViewLastestTimeServerOperation.setText(dataSnapshot.getValue().toString());
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
                init();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bạn có chắc lưu lại thông tin cấu hình?",1);

            }
        });
        btnDeleteValueDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bạn có chắc xóa dữ liệu các cảm biến?",2);

            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bạn có chắc muốn đăng xuất khỏi hệ thống?",3);
            }
        });
    }
    private void showDialog(String message, final int choice){
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nhắc nhở");
        builder.setMessage(message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if (choice == 1) {
                            int number = Integer.valueOf(editTextTimeSaveInDatabase.getText().toString()) * 1000;   // multiple with 1000 to cast to milisecond
                            mData.child(FirebasePath.CONTROLLER_AUTO_OPERATION_PATH).setValue(swAutoOperation.isChecked());
                            mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).child("CallAlert").setValue(chkCall.isChecked());
                            mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).child("SMSAlert").setValue(chkMessageGSM.isChecked());
                            mData.child(FirebasePath.CONTROLLER_ALERT_TYPE_CONFIG_PATH).child("InternetAlert").setValue(chkMessageFCM.isChecked());
                            mData.child(FirebasePath.TIME_SAVE_IN_DATABASE_PATH).setValue(number);
                        } else if (choice == 2) {
                            mData.child(FirebasePath.SENSOR_VALUE_DATABASE_PATH).setValue("0");
                        } else if (choice == 3) {
                            Intent intent = new Intent(getContext(), SignInActivity.class);
                            FirebaseAuth.getInstance().signOut();
                            startActivity(intent);
                            getActivity().finish();
                        }
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
