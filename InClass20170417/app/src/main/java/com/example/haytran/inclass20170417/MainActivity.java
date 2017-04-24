package com.example.haytran.inclass20170417;

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
        switch(view.getId()){
            case R.id.btnArrayAdapter:
                intent = new Intent(MainActivity.this,ArrayAdapterActivity.class);
                startActivity(intent);
            break;

            case R.id.btnSimpleAdapter:
                intent = new Intent(MainActivity.this,SimpleAdapterActivity.class);
                startActivity(intent);
                break;

            case R.id.btnBaseAdapter:
                intent = new Intent(MainActivity.this,BaseAdapterActivity.class);
                startActivity(intent);
                break;

        }
    }
}
