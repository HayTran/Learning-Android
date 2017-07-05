package com.a20170208.tranvanhay.appat.DashBoardFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.a20170208.tranvanhay.appat.ConfigurationSystemActivity;
import com.a20170208.tranvanhay.appat.MACAndIDMappingSettingActivity;
import com.a20170208.tranvanhay.appat.SignInActivity;
import com.a20170208.tranvanhay.appat.R;
import com.a20170208.tranvanhay.appat.ZoneSettingActivity;
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
        customMenuArrayList.add(new CustomMenu(0,"Thiết lập định danh",R.drawable.ic_identify_black_24dp));
        customMenuArrayList.add(new CustomMenu(1,"Thiết lập khu vực",R.drawable.ic_position_black_24dp));
        customMenuArrayList.add(new CustomMenu(2,"Cấu hình hệ thống",R.drawable.ic_settings_black_24dp));
        customMenuArrayList.add(new CustomMenu(3,"Đăng xuất",R.drawable.ic_sign_out_off_black_24dp));
        menuArrayAdapter = new MenuArrayAdapter(getContext(),R.layout.menu_row,customMenuArrayList);
        listView.setAdapter(menuArrayAdapter);
    }

    private void addEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (customMenuArrayList.get(position).getId() == 0){
                    intent = new Intent(getContext(), MACAndIDMappingSettingActivity.class);
                    startActivity(intent);
                }  else if (customMenuArrayList.get(position).getId() == 1) {
                    intent = new Intent(getContext(), ZoneSettingActivity.class);
                    startActivity(intent);
                }   else if (customMenuArrayList.get(position).getId() == 2) {
                    intent = new Intent(getContext(), ConfigurationSystemActivity.class);
                    startActivity(intent);
                }   else if (customMenuArrayList.get(position).getId() == 3) {
                    intent = new Intent(getContext(), SignInActivity.class);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }



}
