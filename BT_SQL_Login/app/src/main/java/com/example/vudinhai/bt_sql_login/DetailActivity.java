package com.example.vudinhai.bt_sql_login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<Users> arrayList;

    ArrayAdapter arrayAdapter;

    DBHelper db;

    EditText edt1, edt2;

    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new DBHelper(DetailActivity.this);

        listView = (ListView)findViewById(R.id.list);

        arrayList = db.getAllUsers();
        arrayAdapter = new ArrayAdapter(DetailActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                user = db.getSingleAccount(arrayList.get(position).getId());


                Toast.makeText(DetailActivity.this, user.toString(), Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle(getResources().getString(R.string.app_name));
                builder.setMessage("Insert account");
                builder.setCancelable(false);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater inflater = LayoutInflater.from(DetailActivity.this);
                        View view = inflater.inflate(R.layout.layout_insert,null);
                        edt1 = (EditText)view.findViewById(R.id.editText);
                        edt2 = (EditText)view.findViewById(R.id.editText2);

                        edt1.setText(user.getEmail().toString());
                        edt2.setText(user.getPassword().toString());

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailActivity.this);
                        builder1.setTitle(getResources().getString(R.string.app_name));
                        builder1.setMessage(getResources().getString(R.string.insert));
                        builder1.setView(view);
                        builder1.setCancelable(false);

                        builder1.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //db.insertAccount(edt1.getText().toString(),edt2.getText().toString());



                                db.updateAccount(user.getId(),
                                                    edt1.getText().toString(),
                                                    edt2.getText().toString());

                                arrayAdapter.clear();
                                arrayAdapter.addAll(db.getAllUsers());
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });

                        AlertDialog alertDialog1 = builder1.create();
                        alertDialog1.show();
                    }
                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.deleteAccount(user.getId());

                        arrayAdapter.clear();
                        arrayAdapter.addAll(db.getAllUsers());
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                return false;
            }
        });

    }
}
