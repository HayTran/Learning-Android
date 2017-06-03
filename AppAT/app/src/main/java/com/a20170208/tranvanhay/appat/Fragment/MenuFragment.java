package com.a20170208.tranvanhay.appat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.a20170208.tranvanhay.appat.Activity.SettingActivity;
import com.a20170208.tranvanhay.appat.Activity.SignInActivity;
import com.a20170208.tranvanhay.appat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Van Hay on 30-May-17.
 */

public class MenuFragment extends Fragment {
    private static final String TAG = MenuFragment.class.getSimpleName();
    ArrayList <CustomMenu> customMenuArrayList;
    MenuArrayAdapter menuArrayAdapter;
    ListView listView;
    public MenuFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
        init();
        addEvent();
    }

    private void addControl(View view) {
        listView = (ListView)view.findViewById(R.id.listView);
    }

    private void init() {
        customMenuArrayList = new ArrayList<>();
        customMenuArrayList.add(new CustomMenu("Sign out",R.drawable.ic_add_to_photos_black_24dp));
        customMenuArrayList.add(new CustomMenu("Setting",R.drawable.ic_poll_black_24dp));
        menuArrayAdapter = new MenuArrayAdapter(getContext(),R.layout.menu_row,customMenuArrayList);
        listView.setAdapter(menuArrayAdapter);
    }

    private void addEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (customMenuArrayList.get(position).getName().equals("Sign out")){
                    intent = new Intent(getContext(), SignInActivity.class);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(intent);
                    getActivity().finish();
                } else if (customMenuArrayList.get(position).getName().equals("Setting")) {
                    intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



}
