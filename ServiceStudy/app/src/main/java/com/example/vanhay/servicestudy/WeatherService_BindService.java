package com.example.vanhay.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeatherService_BindService extends Service {
    private static final String TAG = WeatherService_BindService.class.getSimpleName();
    private static final Map<String,String> weatherData = new HashMap<String,String>();
    private final IBinder binder = new LocalWeatherBinder();
    public class LocalWeatherBinder extends Binder{
        public WeatherService_BindService getService(){
            return WeatherService_BindService.this;
        }
    }

    public WeatherService_BindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind");
       return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG,"onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }
    // Trả về thông tin thời tiết ứng với địa điểm của ngày hiện tại.
    public String getWeatherToday(String location) {
        Date now= new Date();
        DateFormat df= new SimpleDateFormat("dd-MM-yyyy");

        String dayString = df.format(now);
        String keyLocAndDay = location + "$"+ dayString;

        String weather=  weatherData.get(keyLocAndDay);
        //
        if(weather != null)  {
            return weather;
        }
        //
        String[] weathers = new String[]{"Rainy", "Hot", "Cool", "Warm" ,"Snowy"};

        // Giá trị ngẫu nhiên từ 0 tới 4
        int i= new Random().nextInt(5);

        weather =weathers[i];
        weatherData.put(keyLocAndDay, weather);
        //
        return weather;
    }
}
