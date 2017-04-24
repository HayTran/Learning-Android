package com.example.haytran.inclass20170414;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
    public void transmitDataFromTopToBottom(String msg, int img){
        BottomFragment bottomFragment = (BottomFragment)getSupportFragmentManager().findFragmentById(R.id.bottomFragment);
        bottomFragment.showMessage(msg,img);
    }
}
