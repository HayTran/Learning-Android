package com.a20170208.tranvanhay.respberry3;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Van Hay on 4/27/2017.
 */

public class ARPNetwork extends AsyncTask <String, Integer, String>{
    private static final String TAG = ARPNetwork.class.getSimpleName();
    // Instance for Realtime Database
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    /**
     * Try to extract a hardware MAC address from a given IP address using the
     * ARP cache (/proc/net/arp).<br>
     * <br>
     * We assume that the file has this structure:<br>
     * <br>
     * IP address       HW type     Flags       HW address            Mask     Device
     * 192.168.18.11    0x1         0x2         00:04:20:06:55:1a     *        eth0
     * 192.168.18.36    0x1         0x2         00:22:43:ab:2a:5b     *        eth0
     *
     * @param params
     * @return the MAC from the ARP cache
     */

    @Override
    protected String doInBackground(String... params) {
            // params[0] is IP address want to resolve to MAC address
        if (params[0] == null)
            return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4 && params[0].equals(splitted[0])) {
                    // Basic sanity check
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {
                        Log.d(TAG,"Resolve successfully! MAC: " + mac);
                        return mac;
                    } else {
                        Log.d(TAG,"Resolve failed, don't have ARP table");
                        return null;
                    }
                } else {
                    Log.d(TAG,"Resolve failed, not yet connect to this host");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,"Exception catched: " + e.toString());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG,"Resolve finished!. Mac Address: " + s);
    }
}
