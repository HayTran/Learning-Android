package com.example.haytran.inclass20170414;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Hay Tran on 14/04/2017.
 */

public class CustomPaperApdapter extends PagerAdapter {
    Context context;
    ArrayList<Integer> arrayListLayout;

    public CustomPaperApdapter(Context context, ArrayList<Integer> arrayListLayout) {
        this.context = context;
        this.arrayListLayout = arrayListLayout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View layout = layoutInflater.inflate(arrayListLayout.get(position),container);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayListLayout.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
