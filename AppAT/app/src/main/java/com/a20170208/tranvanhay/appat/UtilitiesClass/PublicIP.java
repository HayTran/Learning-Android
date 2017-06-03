package com.a20170208.tranvanhay.appat.UtilitiesClass;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Created by Van Hay on 4/26/2017.
 */

public class PublicIP extends AsyncTask <String,Integer,String>{
    private static final String TAG = PublicIP.class.getSimpleName();
    Context context;
    public PublicIP(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        // Verbose variable is used to specify if logging or not
        Boolean verbose = true;
        String stringUrl = "https://ipinfo.io/ip";
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(verbose) {
                int responseCode = conn.getResponseCode();
                Log.d(TAG,"\nSending 'GET' request to URL : " + url);
                Log.d(TAG,"Response Code : " + responseCode);
            }
            StringBuffer response = new StringBuffer();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if(verbose) {
                // Print result
                Log.d(TAG,"My Public IP address:" + response.toString());
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, "Public IP:" + s.toString(), Toast.LENGTH_SHORT).show();
    }
}
