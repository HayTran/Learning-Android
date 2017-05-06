package com.example.vanhay.servicestudy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BindService_MainActivity extends AppCompatActivity {
    private boolean binded = false;
    private WeatherService_BindService weatherService_bindService;
    private TextView textViewWeather;
    private EditText editTextLocation;
    private Button btnCheckWeather;
    ServiceConnection weatherServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherService_BindService.LocalWeatherBinder binder = (WeatherService_BindService.LocalWeatherBinder) service;
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_service__main);
        textViewWeather  = (TextView)findViewById(R.id.textViewWeather);
        editTextLocation = (EditText)findViewById(R.id.editTextLocation);
        btnCheckWeather = (Button)findViewById(R.id.btnCheckWeather);
        btnCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeather();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,WeatherService_BindService.class);
        this.bindService(intent,weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(binded == true){
            this.unbindService(weatherServiceConnection);
            binded = false;
        }
    }
    public void showWeather(){
        String location = editTextLocation.getText().toString();
        String weather = this.weatherService_bindService.getWeatherToday(location);
        textViewWeather.setText(weather);
    }
}
