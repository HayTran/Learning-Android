package com.example.haytran.inclass20170414;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    TextView textView;
    ImageView imageView;
    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom,container,false);
        textView = (TextView)view.findViewById(R.id.textViewBottomFragment);
        imageView = (ImageView)view.findViewById(R.id.imgViewBottomFragment);
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_bottom, container, false);
        return view;
    }
    public void showMessage(String txt, int img){
        this.textView.setText(txt);
        this.imageView.setImageResource(img);
    }

}
