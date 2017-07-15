package com.a20170208.tranvanhay.appat.UtilitiesClass;

import java.io.Serializable;

/**
 * Created by Van Hay on 31-May-17.
 */

public class SensorNode implements Serializable{
    private String MACAddr;
    private String ID;
    private int zone;       // group sensors  and powdev nodes into zones
    private double strengthWifi, temperature, humidity;
    private  double meanFlameValue, lightIntensity, MQ2,MQ7;
    // Declare value variable for configuration
    private double configTemperature, configHumidity, configMeanFlameValue;
    private double configLightIntensity, configMQ2, configMQ7;

    private String timeSend;
    public SensorNode() {
    }

    public SensorNode(String MACAddr, String ID, double strengthWifi, double temperature, double humidity, double meanFlameValue, double lightIntensity, double MQ2, double MQ7, String timeSend) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.strengthWifi = strengthWifi;
        this.temperature = temperature;
        this.humidity = humidity;
        this.meanFlameValue = meanFlameValue;
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

    public double getMeanFlameValue() {
        return meanFlameValue;
    }

    public void setMeanFlameValue(double meanFlameValue) {
        this.meanFlameValue = meanFlameValue;
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

        this.timeSend = TimeAndDate.convertMilisecondToTimeAndDate(timeSend);
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public double getConfigTemperature() {
        return configTemperature;
    }

    public void setConfigTemperature(double configTemperature) {
        this.configTemperature = configTemperature;
    }

    public double getConfigHumidity() {
        return configHumidity;
    }

    public void setConfigHumidity(double configHumidity) {
        this.configHumidity = configHumidity;
    }

    public double getConfigMeanFlameValue() {
        return configMeanFlameValue;
    }

    public void setConfigMeanFlameValue(double configMeanFlameValue) {
        this.configMeanFlameValue = configMeanFlameValue;
    }

    public double getConfigLightIntensity() {
        return configLightIntensity;
    }

    public void setConfigLightIntensity(double configLightIntensity) {
        this.configLightIntensity = configLightIntensity;
    }

    public double getConfigMQ2() {
        return configMQ2;
    }

    public void setConfigMQ2(double configMQ2) {
        this.configMQ2 = configMQ2;
    }

    public double getConfigMQ7() {
        return configMQ7;
    }

    public void setConfigMQ7(double configMQ7) {
        this.configMQ7 = configMQ7;
    }

    @Override
    public String toString() {
        return "SensorNode{" +
                "timeSend='" + timeSend + '\'' +
                ", MQ7=" + MQ7 +
                ", MQ2=" + MQ2 +
                ", lightIntensity=" + lightIntensity +
                ", meanFlameValue=" + meanFlameValue +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", strengthWifi=" + strengthWifi +
                ", zone=" + zone +
                ", ID='" + ID + '\'' +
                ", MACAddr='" + MACAddr + '\'' +
                '}';
    }
}
