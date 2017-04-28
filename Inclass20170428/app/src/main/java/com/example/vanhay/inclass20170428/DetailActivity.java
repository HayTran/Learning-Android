package com.example.vanhay.inclass20170428;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    FlagItem flagItem;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = (TextView)findViewById(R.id.textViewDetailActivity);
        imageView = (ImageView)findViewById(R.id.imageViewDetailActivity);
        Intent intent = getIntent();
        flagItem = (FlagItem)intent.getSerializableExtra("SelectedObject");
        textView.setText(flagItem.getName().toString());
        imageView.setImageResource(flagItem.getResource());
        Toast.makeText(this, "Data: " + intent.getStringExtra("Hay"), Toast.LENGTH_SHORT).show();
    }
}
