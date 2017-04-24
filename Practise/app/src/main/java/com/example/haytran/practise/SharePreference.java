package com.example.haytran.practise;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class SharePreference extends AppCompatActivity {
    EditText editTextUsername,editTextPassword;
    CheckBox checkBox;
    String filename = "HAY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preference);
        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        checkBox = (CheckBox)findViewById(R.id.checkboxRemember);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharePreference = getSharedPreferences(filename,MODE_PRIVATE);
        editTextUsername.setText(sharePreference.getString("username",null));
        editTextPassword.setText(sharePreference.getString("password",null));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharePreference = getSharedPreferences(filename, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePreference.edit();

        if (checkBox.isChecked()) {
            editor.putString("username", editTextUsername.getText().toString());
            editor.putString("password", editTextPassword.getText().toString());
            editor.commit();
        }
        else {
            editor.clear();
        }
    }
}
