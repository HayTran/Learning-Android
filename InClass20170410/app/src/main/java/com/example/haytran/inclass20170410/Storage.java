package com.example.haytran.inclass20170410;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Storage extends AppCompatActivity {
    Button btnReadData, btnWriteData;
    EditText editTextDataInput;
    TextView textViewReadData;
    String filename = "nhung";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnWriteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fos = openFileOutput(filename,MODE_APPEND);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(editTextDataInput.getText().toString());
                    bw.close();
                    osw.close();
                    fos.close();
                    Toast.makeText(Storage.this, "Save successfully", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnReadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = openFileInput(filename);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String line = br.readLine();
                    StringBuilder sb = new StringBuilder();
                    while (line != null){
                        sb.append(line);
                        line = br.readLine();
                        sb.append("\n");
                    }
                    textViewReadData.setText(sb.toString());
                    br.close();
                    isr.close();
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addControl() {
        btnReadData = (Button)findViewById(R.id.btnReadDataStorageActivity);
        btnWriteData = (Button)findViewById(R.id.btnWriteDataStorageActivity);
        editTextDataInput = (EditText)findViewById(R.id.editTextDataStorageActivity);
        textViewReadData  = (TextView)findViewById(R.id.textViewDataStorageActivity);
    }
}
