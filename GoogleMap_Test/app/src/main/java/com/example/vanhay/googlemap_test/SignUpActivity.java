package com.example.vanhay.googlemap_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    FirebaseAuth mAuth;
    Button btnSignUp;
    TextView textViewChangeToLogInActivity;
    EditText editTextDisplayName, editTextEmail, editTextPassword, editTextConfirmPassword;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mapping();
        init();
        addControls();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void addControls() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayname, email, password, confirmPassword;
                displayname = editTextDisplayName.getText().toString();
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                confirmPassword  = editTextConfirmPassword.getText().toString();
                if (displayname.equals("") || email.equals("")
                        || password.equals("") || confirmPassword.equals("")){
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập vào ô còn trống", Toast.LENGTH_SHORT).show();
                } else if (password.equals(confirmPassword)){
                    createNewUser(email,password);
                } else {
                    Toast.makeText(SignUpActivity.this, "Hai mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewChangeToLogInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        btnSignUp = (Button)findViewById(R.id.btnSignUp_SignUpActivity);
        textViewChangeToLogInActivity = (TextView)findViewById(R.id.textViewChangeToLogIn_SignUpActivity);
        editTextDisplayName = (EditText)findViewById(R.id.editTextDisplayName_SignUpActivity);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail_SignUpActivity);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword_SignUpActivity);
        editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword_SignUpActivity);
    }
    private void createNewUser(String email,String password){
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            updateProfile(editTextDisplayName.getText().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Tạo tài khoản thất bại! Vui lòng kiểm tra lại tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updateProfile(String displayname ){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayname)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            stepToUpdateProfileActivity();
                            Log.d(TAG, "User profile updated.");
                        } else {
                            Toast.makeText(SignUpActivity.this, "Cập nhật tài khoản không thành công. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    private void stepToUpdateProfileActivity(){
        Intent intent = new Intent(SignUpActivity.this,SelectGroupActivity.class);
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
