package com.a20170208.tranvanhay.respberry3;

/**
 * Created by Tran Van Hay on 3/24/2017.
 */

public class NodeSensor {
    private int strengthWifi,temperature, humidity;
    private double  flameValue0, flameValue1, flameValue2,flameValue3, lightIntensity, MQ2,MQ7;
    private String MACAddr;
    public NodeSensor() {
    }

    public NodeSensor(int strengthWifi, int temperature, int humidity,
                      double flameValue0, double flameValue1, double flameValue2, double flameValue3,
                      double lightIntensity, double MQ2, double MQ7, String MACAddr) {
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
        this.MACAddr = MACAddr;
    }

    public int getStrengthWifi() {
        return strengthWifi;
    }

    public void setStrengthWifi(int strengthWifi) {
        this.strengthWifi = strengthWifi;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
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
                '}';
    }
}