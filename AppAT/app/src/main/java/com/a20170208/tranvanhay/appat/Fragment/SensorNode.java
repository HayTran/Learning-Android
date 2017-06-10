package com.a20170208.tranvanhay.appat.Fragment;

/**
 * Created by Van Hay on 31-May-17.
 */

public class SensorNode {
    private String MACAddr;
    private String ID;
    private int zone;       // group sensors  and powdev nodes into zones
    private double strengthWifi, temperature, humidity;
    private  double flameValue0, flameValue1, flameValue2,flameValue3, lightIntensity, MQ2,MQ7;
    private String timeSend;
    public SensorNode() {
    }

    public SensorNode(String MACAddr, String ID, double strengthWifi, double temperature, double humidity, double flameValue0, double flameValue1, double flameValue2, double flameValue3, double lightIntensity, double MQ2, double MQ7, String timeSend) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.strengthWifi = strengthWifi;
        this.temperature = temperature;
        this.humidity = humidity;
        this.flameValue0 = flameValue0;
        this.flameValue1 = flameValue1;
        this.flameValue2 = flameValue2;
        this.flameValue3 = flameValue3;
        this.lightIntensity = lightIntensity;
        this.MQ2 = MQ2;
        this.MQ7 = MQ7;
        this.timeSend = timeSend;
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

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "SensorNode{" +
                "MACAddr='" + MACAddr + '\'' +
                ", ID=" + ID +
                ", strengthWifi=" + strengthWifi +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", flameValue0=" + flameValue0 +
                ", flameValue1=" + flameValue1 +
                ", flameValue2=" + flameValue2 +
                ", flameValue3=" + flameValue3 +
                ", lightIntensity=" + lightIntensity +
                ", MQ2=" + MQ2 +
                ", MQ7=" + MQ7 +
                ", timeSend='" + timeSend + '\'' +
                '}';
    }
}
