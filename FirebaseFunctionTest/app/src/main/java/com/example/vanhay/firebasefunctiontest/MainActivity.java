package com.example.vanhay.firebasefunctiontest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final  String TAG  = MainActivity.class.getSimpleName();
    DatabaseReference mData;
    Button btnStart;
    TextView textViewGottenData;
    EditText editTextBirth, editTextSex, editTextName;
    Person setPerson;
    Person gottenPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        addControls();
    }

    private void addControls() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPerson = new Person(editTextName.getText().toString(),
                        Integer.valueOf(editTextBirth.getText().toString()),
                        editTextSex.getText().toString());
                mData.child("hay").child("nhung").setValue(setPerson);
            }
        });
       mData.child("hay").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                   Log.d(TAG,dataSnapshot1.getValue().toString());
                   Toast.makeText(MainActivity.this, dataSnapshot1.getValue().toString() + " ", Toast.LENGTH_SHORT).show();
                   textViewGottenData.setText(dataSnapshot1.getValue().toString());
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }

    private void mapping() {
        mData = FirebaseDatabase.getInstance().getReference();
        btnStart = (Button)findViewById(R.id.btnStart);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextBirth = (EditText)findViewById(R.id.editTextBirth);
        editTextSex = (EditText)findViewById(R.id.editTextSex);
        textViewGottenData = (TextView)findViewById(R.id.textViewGottenData);

    }
}
