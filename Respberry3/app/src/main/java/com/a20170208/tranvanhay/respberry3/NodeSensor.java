package com.a20170208.tranvanhay.respberry3;

/**
 * Created by Tran Van Hay on 3/24/2017.
 */

public class NodeSensor {
    private double strengthWifi,temperature, humidity;
    private double  flameValue0, flameValue1, flameValue2,flameValue3, lightIntensity, MQ2,MQ7;
    private String MACAddr;
    private int [] arrayBytes;
    private boolean isConfirmed;

    public NodeSensor() {
    }

    public NodeSensor(int[] arrayBytes, String MACAddr, boolean isConfirmed) {
        this.arrayBytes = arrayBytes;
        this.isConfirmed = isConfirmed;
        this.MACAddr = MACAddr;
    }
    protected void convertValue(){
        temperature = arrayBytes[0] + arrayBytes[1]*256;
        humidity = arrayBytes[2]+ arrayBytes[3]*256;
        flameValue0 = arrayBytes[4] + arrayBytes[5]*256;
        flameValue0 = 100 - (flameValue0/1024)*100;
        flameValue1 = arrayBytes[6] + arrayBytes[7]*256;
        flameValue1 = 100 - (flameValue1/1024)*100;
        flameValue2 = arrayBytes[8] + arrayBytes[9]*256;
        flameValue2 = 100 - (flameValue2/1024)*100;
        flameValue3 = arrayBytes[10] + arrayBytes[11]*256;
        flameValue3 = 100 - (flameValue3/1024)*100;
        lightIntensity = arrayBytes[12] + arrayBytes[13]*256;
        MQ2 = arrayBytes[14] + arrayBytes[15]*256;
        MQ7 = arrayBytes[16] + arrayBytes[17]*256;
        strengthWifi = arrayBytes[18];
    }



}