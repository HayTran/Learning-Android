package com.example.haytran.inclass20170414;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    EditText editText;
    Button buttonSend;
    FragmentActivity fragmentActivity;
    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top,container,false);
        editText = (EditText)view.findViewById(R.id.editTextTopFragment);
        buttonSend = (Button)view.findViewById(R.id.btnSendDataToBottomFragment);
//        return inflater.inflate(R.layout.fragment_top, container, false);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyText();
            }
        });
        return  view;
    }
    public void applyText() {
            String text = editText.getText().toString();
            int img = R.drawable.firebase;
            this.fragmentActivity.transmitDataFromTopToBottom(text,img);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            this.fragmentActivity = (FragmentActivity) context;
        }
    }
}
