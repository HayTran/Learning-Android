package com.example.haytran.inclass20170410;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SharePreferences extends AppCompatActivity {
    EditText username, password;
    Button btnLogin,btnExit;
    CheckBox checkBoxSaveInfo;
    String fileSharedPreferences = "LOGIN_STATUS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preferences);
        addControl();
        init();
        addEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(fileSharedPreferences,MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("isChecked",false);
        if(check){
            username.setText(sharedPreferences.getString("Username",null));
            password.setText(sharedPreferences.getString("Password",null));

        }
        checkBoxSaveInfo.setChecked(check);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(fileSharedPreferences,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(checkBoxSaveInfo.isChecked()){
            editor.putString("Username",username.getText().toString());
            editor.putString("Password",password.getText().toString());
            editor.putBoolean("isChecked",checkBoxSaveInfo.isChecked());
        }
        else {
            editor.clear();
        }
        editor.commit(); // close editing shared file
    }

    private void addEvent() {

    }

    private void init() {

    }

    private void addControl() {
        username = (EditText)findViewById(R.id.editTextUsernameSharePreferenceActivity);
        password = (EditText)findViewById(R.id.editTextPasswordSharePreferenceActivity);
        btnLogin = (Button)findViewById(R.id.btnLoginSharePreferenceActivity);
        btnExit = (Button)findViewById(R.id.btnExitSharePreferenceActivity);
        checkBoxSaveInfo = (CheckBox)findViewById(R.id.checkboxSaveInfoSharePreferenceActivity);
    }
}
