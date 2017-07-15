package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tran Van Hay on 3/24/2017.
 */

public class SensorNode {
    private static final String TAG = SensorNode.class.getSimpleName();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private String MACAddr; // help server recognize
    private String ID;      // help user recognize
    private int zone;       // group sensors  and powdev nodes into zones
    private double strengthWifi,temperature, humidity;
    private double  flameValue0, flameValue1, flameValue2,flameValue3, meanFlameValue, lightIntensity, MQ2,MQ7;
        // Declare value variable for configuration
    private double configTemperature, configHumidity, configMeanFlameValue;
    private double configLightIntensity, configMQ2, configMQ7;
    private long timeSend;
    private long timeSaveInDatabase;
    private int [] arrayBytes;
    private int exceedAlertCount;
    private int exceedImplementCount;

        // Declare paths variable in Firebase
    private String listPath;
    private String currentValuePath;
    private String valueConfigPath;
    private String valueDatabasePath;
    private String zonePath;

    public SensorNode() {
    }

    public SensorNode(String MACAddr, String ID, int[] arrayBytes) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.arrayBytes = arrayBytes;
        this.listPath  = FirebasePath.SENSOR_LIST_PATH + this.ID;
        this.valueDatabasePath = FirebasePath.SENSOR_VALUE_DATABASE_PATH + this.ID;
        this.currentValuePath = FirebasePath.SENSOR_CURRENT_VALUE_PATH + this.ID;
        this.valueConfigPath = FirebasePath.SENSOR_VALUE_CONFIG_PATH + this.ID;
        this.zonePath = FirebasePath.ZONE_SENSOR_NODE_CONFIG_PATH + this.ID;
        this.convertValue();
        triggerConfigValue();
        exceedImplementCount = 0;
        exceedAlertCount = 0;
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
        meanFlameValue = (flameValue0 + flameValue1 + flameValue2 + flameValue3)/4;
        lightIntensity = arrayBytes[10] + arrayBytes[11]*256;
        MQ2 = arrayBytes[12] + arrayBytes[13]*256;
        MQ7 = arrayBytes[14] + arrayBytes[15]*256;
        strengthWifi = arrayBytes[16];
    }
        // Send each node sensor's value to firebase
    public void sendToFirebase(){
        mData.child(this.listPath).setValue(System.currentTimeMillis());
        mData.child(this.currentValuePath).child("MACAddr").setValue(MACAddr);
        mData.child(this.currentValuePath).child("zone").setValue(zone);
        mData.child(this.currentValuePath).child("strengthWifi").setValue(strengthWifi);
        mData.child(this.currentValuePath).child("temperature").setValue(temperature);
        mData.child(this.currentValuePath).child("humidity").setValue(humidity);
        mData.child(this.currentValuePath).child("meanFlameValue").setValue(meanFlameValue);
        mData.child(this.currentValuePath).child("lightIntensity").setValue(lightIntensity);
        mData.child(this.currentValuePath).child("MQ2").setValue(MQ2);
        mData.child(this.currentValuePath).child("MQ7").setValue(MQ7);
        mData.child(this.currentValuePath).child("timeSend").setValue(System.currentTimeMillis());
    }

    public void saveInDatabaseInFirebase(){
        mData.child(this.valueDatabasePath).child(timeSend+"").child("temperature").setValue(temperature);
        mData.child(this.valueDatabasePath).child(timeSend+"").child("humidity").setValue(humidity);
        mData.child(this.valueDatabasePath).child(timeSend+"").child("meanFlameValue").setValue(meanFlameValue);
        mData.child(this.valueDatabasePath).child(timeSend+"").child("lightIntensity").setValue(lightIntensity);
        mData.child(this.valueDatabasePath).child(timeSend+"").child("MQ2").setValue(MQ2);
        mData.child(this.valueDatabasePath).child(timeSend+"").child("MQ7").setValue(MQ7);
    }
        // Get each node sensor's config value from firebase
    private void triggerConfigValue(){
        mData.child(this.valueConfigPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals("temperature")){
                        configTemperature = Double.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("humidity")){
                        configHumidity = Double.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("meanFlameValue")){
                        configMeanFlameValue = Double.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("lightIntensity")){
                        configLightIntensity = Double.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("MQ2")){
                        configMQ2 = Double.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("MQ7")){
                        configMQ7 = Double.valueOf(dataSnapshot1.getValue().toString());
                    }
                }
                Log.d(TAG,"Config Temperature: " + configTemperature );
                Log.d(TAG,"Config Humidity: " + configHumidity);
                Log.d(TAG,"Config meanFlameValue: " + configMeanFlameValue );
                Log.d(TAG,"Config lightIntensity: " + configLightIntensity );
                Log.d(TAG,"Config MQ2: " + configMQ2 );
                Log.d(TAG,"Config MQ7: " + configMQ7 );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(zonePath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                zone = Integer.valueOf(dataSnapshot.getValue().toString());
                Log.d(TAG,"Zone: " + zone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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


    public int[] getArrayBytes() {
        return arrayBytes;
    }

    public void setArrayBytes(int[] arrayBytes) {
        this.arrayBytes = arrayBytes;
        this.convertValue();
    }

    public long getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(long timeSend) {
        this.timeSend = timeSend;
    }

    public long getTimeSaveInDatabase() {
        return timeSaveInDatabase;
    }

    public void setTimeSaveInDatabase(long timeSaveInDatabase) {
        this.timeSaveInDatabase = timeSaveInDatabase;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getExceedAlertCount() {
        return exceedAlertCount;
    }

    public void setExceedAlertCount(int exceedAlertCount) {
        this.exceedAlertCount = exceedAlertCount;
    }

    public int getExceedImplementCount() {
        return exceedImplementCount;
    }

    public void setExceedImplementCount(int exceedImplementCount) {
        this.exceedImplementCount = exceedImplementCount;
    }

    @Override
    public String toString() {
        return "SensorNode{" +
                "ID='" + ID + '\'' +
                ", zone=" + zone +
                ", strengthWifi=" + strengthWifi +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", meanFlameValue=" + meanFlameValue +
                ", lightIntensity=" + lightIntensity +
                ", MQ7=" + MQ7 +
                ", MQ2=" + MQ2 +
                ", timeSend='" + TimeAndDate.currentTime + '\'' +
                '}';
    }
}