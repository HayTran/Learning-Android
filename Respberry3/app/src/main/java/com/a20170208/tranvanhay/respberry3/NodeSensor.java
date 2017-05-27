package com.a20170208.tranvanhay.respberry3;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tran Van Hay on 3/24/2017.
 */

public class NodeSensor {
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private String MACAddr; // help server recognize
    private String ID;      // help user recognize
    private double strengthWifi,temperature, humidity;
    private double  flameValue0, flameValue1, flameValue2,flameValue3, lightIntensity, MQ2,MQ7;
    private String timeSend;
    private int [] arrayBytes;


    public NodeSensor() {
    }

    public NodeSensor(String MACAddr, String ID, int[] arrayBytes) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.arrayBytes = arrayBytes;
        this.convertValue();
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
    public void sendToFirebase(){
        mData.child("SocketServer").child("NodeList").child("NodeSensor").child(this.ID).setValue(TimeAndDate.currentTime);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("MACAddress").setValue(MACAddr);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("strengthWifi").setValue(strengthWifi);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("temperature").setValue(temperature);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("humidity").setValue(humidity);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("flame0").setValue(flameValue0);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("flame1").setValue(flameValue1);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("flame2").setValue(flameValue2);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("flame3").setValue(flameValue3);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("lightIntensity").setValue(lightIntensity);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("MQ2").setValue(MQ2);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("MQ7").setValue(MQ7);
        mData.child("SocketServer").child("NodeDetails").child("NodeSensor").child(this.ID).child("timeSend").setValue(timeSend);
    }

    public String getMACAddr() {
        return MACAddr;
    }

    public void setMACAddr(String MACAddr) {
        this.MACAddr = MACAddr;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public int[] getArrayBytes() {
        return arrayBytes;
    }

    public void setArrayBytes(int[] arrayBytes) {
        this.arrayBytes = arrayBytes;
        this.convertValue();
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
                ", sendTime='" + timeSend + '\'' +
                ", MACAddr='" + MACAddr + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}