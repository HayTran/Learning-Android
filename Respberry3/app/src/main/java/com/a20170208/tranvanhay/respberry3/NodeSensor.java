package com.a20170208.tranvanhay.respberry3;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tran Van Hay on 3/24/2017.
 */

public class NodeSensor {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private double strengthWifi,temperature, humidity;
    private double  flameValue0, flameValue1, flameValue2,flameValue3, lightIntensity, MQ2,MQ7;
    private String MACAddr;
    private boolean isConfirmed;
    private String sendTime;
    private int [] arrayBytes;

    public NodeSensor() {
    }

    public NodeSensor(int[] arrayBytes, String MACAddr, String sendTime, boolean isConfirmed) {
        this.arrayBytes = arrayBytes;
        this.sendTime = sendTime;
        this.MACAddr = MACAddr;
        this.isConfirmed = isConfirmed;
    }
    protected void convertValue(){
        temperature = arrayBytes[0];
        humidity = arrayBytes[1];
        flameValue0 = arrayBytes[2] + arrayBytes[3]*256;
        flameValue0 = 100 - (flameValue0/1024)*100;
        flameValue1 = arrayBytes[4] + arrayBytes[5]*256;
        flameValue1 = 100 - (flameValue1/1024)*100;
        flameValue2 = arrayBytes[6] + arrayBytes[7]*256;
        flameValue2 = 100 - (flameValue2/1024)*100;
        flameValue3 = arrayBytes[8] + arrayBytes[9]*256;
        flameValue3 = 100 - (flameValue3/1024)*100;
        lightIntensity = arrayBytes[10] + arrayBytes[11]*256;
        MQ2 = arrayBytes[12] + arrayBytes[13]*256;
        MQ7 = arrayBytes[14] + arrayBytes[15]*256;
        strengthWifi = arrayBytes[16];
    }
//    public void sendToFirebase(){
//        mData.child("SocketServer").child(MACAddr).child("Temperature").setValue(temperature);
//        mData.child("SocketServer").child(MACAddr).child("Humidity").setValue(humidity);
//        mData.child("SocketServer").child(MACAddr).child("Light Intensity").setValue(lightIntensity);
//        mData.child("SocketServer").child(MACAddr).child("Flame 0").setValue(flameValue0);
//        mData.child("SocketServer").child(MACAddr).child("MQ2").setValue(MQ2);
//        mData.child("SocketServer").child(MACAddr).child("MQ7").setValue(MQ7);
//        mData.child("SocketServer").child(MACAddr).child("isConfirmed").setValue(isConfirmed);
//    }
    public double getStrengthWifi() {
        return strengthWifi;
    }

    public void setStrengthWifi(double strengthWifi) {
        this.strengthWifi = strengthWifi;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getFlameValue0() {
        return flameValue0;
    }

    public void setFlameValue0(double flameValue0) {
        this.flameValue0 = flameValue0;
    }

    public double getFlameValue1() {
        return flameValue1;
    }

    public void setFlameValue1(double flameValue1) {
        this.flameValue1 = flameValue1;
    }

    public double getFlameValue2() {
        return flameValue2;
    }

    public void setFlameValue2(double flameValue2) {
        this.flameValue2 = flameValue2;
    }

    public double getFlameValue3() {
        return flameValue3;
    }

    public void setFlameValue3(double flameValue3) {
        this.flameValue3 = flameValue3;
    }

    public double getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(double lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public double getMQ2() {
        return MQ2;
    }

    public void setMQ2(double MQ2) {
        this.MQ2 = MQ2;
    }

    public double getMQ7() {
        return MQ7;
    }

    public void setMQ7(double MQ7) {
        this.MQ7 = MQ7;
    }

    public String getMACAddr() {
        return MACAddr;
    }

    public void setMACAddr(String MACAddr) {
        this.MACAddr = MACAddr;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "NodeSensor{" +
                "strengthWifi=" + strengthWifi +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", flameValue0=" + flameValue0 +
                ", flameValue1=" + flameValue1 +
                ", flameValue2=" + flameValue2 +
                ", flameValue3=" + flameValue3 +
                ", lightIntensity=" + lightIntensity +
                ", MQ2=" + MQ2 +
                ", MQ7=" + MQ7 +
                ", MACAddr='" + MACAddr + '\'' +
                ", isConfirmed=" + isConfirmed +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }

}