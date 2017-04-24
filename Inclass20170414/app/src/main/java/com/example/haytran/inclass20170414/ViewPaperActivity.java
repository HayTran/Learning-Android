package com.example.haytran.inclass20170414;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewPaperActivity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList <Integer> arrayListLayout;
    CustomPaperApdapter customPaperApdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        arrayListLayout = new ArrayList<>();
        arrayListLayout.add(R.layout.layout_black);
        arrayListLayout.add(R.layout.layout_white);
//        arrayListLayout.add(R.layout.layout_blue);
//        arrayListLayout.add(R.layout.layout_red);
        customPaperApdapter = new CustomPaperApdapter(ViewPaperActivity.this,arrayListLayout);
        viewPager.setAdapter(customPaperApdapter);
    }
}
