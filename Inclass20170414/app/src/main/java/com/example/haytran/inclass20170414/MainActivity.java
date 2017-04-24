package com.example.haytran.inclass20170414;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnChangeToFragmentActivity:
                intent = new Intent(MainActivity.this,FragmentActivity.class);
                startActivity(intent);
                break;
            case R.id.btnChangeToViewPaper:
                intent = new Intent(MainActivity.this,ViewPaperActivity.class);
                startActivity(intent);
                break;
        }
    }
}
