package com.example.haytran.inclass20170421_2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ViewPaperAdapter viewPaperAdapter;
    ViewPager viewPager;
    ImageView imageView1, imageView2, imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        imageView1 = (ImageView)findViewById(R.id.imgView1);
        imageView2= (ImageView)findViewById(R.id.imgView2);
        imageView3= (ImageView)findViewById(R.id.imgView3);

        viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        viewPaperAdapter.addFragment(new P1Fragment());
        viewPaperAdapter.addFragment(new P2Fragment());
        viewPaperAdapter.addFragment(new P3Fragment());
        viewPager.setAdapter(viewPaperAdapter);
        imageView1.setImageResource(R.drawable.intro_selected);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    imageView1.setImageResource(R.drawable.intro_selected);
                    imageView2.setImageResource(R.drawable.intro_default);
                    imageView3.setImageResource(R.drawable.intro_default);
                } else  if (position == 1){
                    imageView1.setImageResource(R.drawable.intro_default);
                    imageView2.setImageResource(R.drawable.intro_selected);
                    imageView3.setImageResource(R.drawable.intro_default);
                } else {
                    imageView1.setImageResource(R.drawable.intro_default);
                    imageView2.setImageResource(R.drawable.intro_default);
                    imageView3.setImageResource(R.drawable.intro_selected);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
