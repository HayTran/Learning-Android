package com.example.vanhay.googlemap_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = LogInActivity.class.getSimpleName();
    Button btnLogIn;
    TextView textViewChangeToSignUpActivity;
    EditText editTextUsername, editTextPassword;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MultiDex.install(this);
        mapping();
        init();
        addControls();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){
            stepToMapsActivity();
        }
    }
    private void init() {
        mAuth = FirebaseAuth.getInstance();

    }
    private void addControls() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                if (email.equals("") || password.equals("")){
                    Toast.makeText(LogInActivity.this, "Vui lòng nhập vào tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    logIn(editTextUsername.getText().toString(),editTextPassword.getText().toString());
                }
                
            }
        });
        textViewChangeToSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void mapping() {
        btnLogIn = (Button)findViewById(R.id.btnLogIn_LogInActivity);
        textViewChangeToSignUpActivity = (TextView)findViewById(R.id.textViewChangeToSignUp_LogInActivity);
        editTextUsername = (EditText)findViewById(R.id.editTextUsername_LogInActivity);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword_LogInActivity);

    }
    private void logIn(String email, String password){
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"Login successfully");
                            stepToMapsActivity();
                        } else {
                            Toast.makeText(LogInActivity.this, "Đăng nhập thất bại. Tài khoản và mật khẩu sai", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
    private void stepToMapsActivity(){
        Intent intent = new Intent(LogInActivity.this,SelectGroupActivity.class);
        startActivity(intent);
        finish();
    }
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý, vui lòng chờ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

}
