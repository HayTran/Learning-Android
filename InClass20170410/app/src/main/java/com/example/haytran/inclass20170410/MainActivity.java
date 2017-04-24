package com.example.haytran.inclass20170410;

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

    public void navigationButton(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnAssetsFolderActivity:
                intent = new Intent(MainActivity.this,AssetFolder.class);
                startActivity(intent);
                break;
            case R.id.btnSharePreferencesActivity:
                intent = new Intent(MainActivity.this,SharePreferences.class);
                startActivity(intent);
                break;
            case R.id.btnStorageActivity:
                intent = new Intent(MainActivity.this,Storage.class);
                startActivity(intent);
                break;
            case R.id.btnSDStorageActivity:
                intent = new Intent(MainActivity.this,SDStorage.class);
                startActivity(intent);
                break;
        }
    }
}
