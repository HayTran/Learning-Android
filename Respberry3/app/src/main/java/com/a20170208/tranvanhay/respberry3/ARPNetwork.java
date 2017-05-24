package com.a20170208.tranvanhay.respberry3;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Van Hay on 03-May-17.
 */

public class ARPNetwork {
    private static final String TAG = ARPNetwork.class.getSimpleName();
    public static String findMAC(String ipAddress){
        // params[0] is IP address want to resolve to MAC address
        if ( ipAddress == null){
            return null;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4 && ipAddress.equals(splitted[0])) {
                    // Basic sanity check
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {
                        Log.d(TAG,"Resolve successfully! MAC: " + mac);
                        return mac;
                    } else {
                        Log.d(TAG,"Resolve failed, don't have ARP table");
                        return null;
                    }
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

}
