package com.example.vudinhai.bt_sql_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword, edt1, edt2;

    Button btnSignIn, btnSignUp;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);

        addControl();
        addEvent();

    }

    private void addEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(db.checkAccount(edtEmail.getText().toString(),edtPassword.getText().toString()) > 0){
                   Toast.makeText(MainActivity.this, getResources().getString(R.string.loginSuccess), Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                   startActivity(intent);
               }else {
                   final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                   builder.setTitle(getResources().getString(R.string.app_name));
                   builder.setMessage(getResources().getString(R.string.incorrect));
                   builder.setCancelable(false);
                   builder.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           InsertAccount();
                       }
                   });
                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           edtEmail.setFocusable(false);
                       }
                   });


                   AlertDialog dialog = builder.create();
                   dialog.show();
               }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertAccount();
            }
        });
    }

    private void InsertAccount() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.layout_insert,null);
        edt1 = (EditText)view.findViewById(R.id.editText);
        edt2 = (EditText)view.findViewById(R.id.editText2);


        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle(getResources().getString(R.string.app_name));
        builder1.setMessage(getResources().getString(R.string.insert));
        builder1.setView(view);
        builder1.setCancelable(false);

        builder1.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.insertAccount(edt1.getText().toString(),edt2.getText().toString());
                edtEmail.setText(edt1.getText().toString());
                edtPassword.setText(edt2.getText().toString());

            }
        });


        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();

    }

    private void addControl() {
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
    }
}
