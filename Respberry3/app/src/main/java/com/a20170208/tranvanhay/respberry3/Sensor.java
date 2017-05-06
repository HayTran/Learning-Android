package com.a20170208.tranvanhay.respberry3;

/**
 * Created by Tran Van Hay on 3/24/2017.
 */

public class Sensor {
    private int temperature, humidity;
    private double lightIntensity, flameValue0, flameValue1, MQ2,MQ7;
    private String MACAddr;
    public Sensor() {
    }

    public Sensor(int temperature, int humidity, double lightIntensity, double flameValue0, double flameValue1, double MQ2, double MQ7, String MACAddr) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.lightIntensity = lightIntensity;
        this.flameValue0 = flameValue0;
        this.flameValue1 = flameValue1;
        this.MQ2 = MQ2;
        this.MQ7 = MQ7;
        this.MACAddr = MACAddr;
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

    public double getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(double lightIntensity) {
        this.lightIntensity = lightIntensity;
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
        return "Sensor{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", lightIntensity=" + lightIntensity +
                ", flameValue0=" + flameValue0 +
                ", flameValue1=" + flameValue1 +
                ", MQ2=" + MQ2 +
                ", MQ7=" + MQ7 +
                ", MACAddr='" + MACAddr + '\'' +
                '}';
    }
}
