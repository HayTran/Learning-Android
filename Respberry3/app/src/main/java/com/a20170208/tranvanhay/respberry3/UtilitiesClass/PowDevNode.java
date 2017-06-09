package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Van Hay on 15-May-17.
 */

public class PowDevNode {
    private static final String TAG = PowDevNode.class.getSimpleName();
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private String MACAddr; // help server recognize
    private String ID;      // help user recognize
    private int zone;       // group sensors  and powdev nodes into zones
    private boolean isEnable;
    private int [] arrayBytes;
    private int strengthWifi, dev0, dev1, buzzer, sim0, sim1;

    private String listPath;
    private String detailsPath;
    private String zonePath;

    public PowDevNode() {
    }

    public PowDevNode(String MACAddr, String ID, int [] arrayBytes) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.arrayBytes = arrayBytes;
        this.listPath = FirebasePath.POWDEV_LIST_PATH + this.ID;
        this.detailsPath = FirebasePath.POWDEV_DETAILS_PATH + this.ID;
        this.zonePath = FirebasePath.ZONE_POWDEV_NODE_CONFIG_PATH + this.ID;
        this.convertValue();
        this.initNodeInFirebase();
        this.triggerValue();
    }
    private void convertValue(){
        strengthWifi = arrayBytes[0];
        dev0 = arrayBytes[1];
        dev1 = arrayBytes[2];
        buzzer = arrayBytes[3];
        sim0 = arrayBytes[4];
        sim1 = arrayBytes[5];
    }
    protected void initNodeInFirebase(){
            // enable powdev when system start. NOTE: Must update by save in sqlite
        this.isEnable = true;
        mData.child(detailsPath).child("MACAddress").setValue(MACAddr);
        mData.child(detailsPath).child("zone").setValue(zone);
        mData.child(detailsPath).child("isEnable").setValue(isEnable);
        mData.child(detailsPath).child("strengthWifi").setValue(strengthWifi);
        mData.child(detailsPath).child("dev0").setValue(dev0);
        mData.child(detailsPath).child("dev1").setValue(dev1);
        mData.child(detailsPath).child("buzzer").setValue(buzzer);
        mData.child(detailsPath).child("sim0").setValue(sim0);
        mData.child(detailsPath).child("sim1").setValue(sim1);
        mData.child(detailsPath).child("timeOperation").setValue(TimeAndDate.currentTime);
        Log.d(TAG,"initNodeInFirebase");
    }

    private void triggerValue() {
        mData.child(detailsPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals("isEnable")) {
                        isEnable = Boolean.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("dev0")) {
                        dev0 = Integer.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("dev1")) {
                        dev1 = Integer.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("buzzer")) {
                        buzzer = Integer.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("sim0")) {
                        sim0 = Integer.valueOf(dataSnapshot1.getValue().toString());
                    }   else if (dataSnapshot1.getKey().equals("sim1")) {
                        sim1 = Integer.valueOf(dataSnapshot1.getValue().toString());
                    }
                }
                Log.d(TAG, "Node has already change!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child(zonePath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                zone = Integer.valueOf(dataSnapshot.getValue().toString()) ;
                Log.d(TAG,"Zone: " + zone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void notifyLastestTimeOperation(){
        mData.child(listPath).setValue(TimeAndDate.currentTime);
        mData.child(detailsPath).child("zone").setValue(zone);
        mData.child(detailsPath).child("timeOperation").setValue(TimeAndDate.currentTime);
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

    public int[] getArrayBytes() {
        return arrayBytes;
    }

    public void setArrayBytes(int[] arrayBytes) {
        this.arrayBytes = arrayBytes;
    }

    public int getStrengthWifi() {
        return strengthWifi;
    }

    public void setStrengthWifi(int strengthWifi) {
        this.strengthWifi = strengthWifi;
        mData.child(detailsPath).child("strengthWifi").setValue(strengthWifi);
    }
    public int getDev0() {
        return dev0;
    }

    public void setDev0(int dev0) {
        this.dev0 = dev0;
    }

    public int getDev1() {
        return dev1;
    }

    public void setDev1(int dev1) {
        this.dev1 = dev1;
    }

    public int getBuzzer() {
        return buzzer;
    }

    public void setBuzzer(int buzzer) {
        this.buzzer = buzzer;
    }

    public int getSim0() {
        return sim0;
    }

    public void setSim0(int sim0) {
        this.sim0 = sim0;
        mData.child(detailsPath).child("sim0").setValue(sim0);
    }

    public int getSim1() {
        return sim1;
    }

    public void setSim1(int sim1) {
        this.sim1 = sim1;
        mData.child(detailsPath).child("sim0").setValue(sim1);
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }
}
