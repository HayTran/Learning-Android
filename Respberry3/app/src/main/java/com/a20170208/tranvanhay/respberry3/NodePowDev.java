package com.a20170208.tranvanhay.respberry3;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Van Hay on 15-May-17.
 */

public class NodePowDev {
    private static final String TAG = NodePowDev.class.getSimpleName();
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private String MACAddr; // help server recognize
    private String ID;      // help user recognize
    private boolean isImplemented;
    private int [] arrayBytes;
    private int strengthWifi, dev0, dev1, buzzer, sim0, sim1;

    public NodePowDev() {
    }

    public NodePowDev(String MACAddr, String ID, int [] arrayBytes, boolean isImplemented) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.arrayBytes = arrayBytes;
        this.isImplemented = isImplemented;
        this.convertValue();
        this.initNodeInFirebase();
        this.triggerFirebase();
    }
    private void convertValue(){
        strengthWifi = arrayBytes[0];
        dev0 = arrayBytes[1];
        dev1 = arrayBytes[2];
        buzzer = arrayBytes[3];
        sim0 = arrayBytes[4];
        sim1 = arrayBytes[5];
    }
    private void initNodeInFirebase(){
        mData.child("SocketServer").child(this.ID).child("MACAddress").setValue(MACAddr);
        mData.child("SocketServer").child(this.ID).child("strengthWifi").setValue(strengthWifi);
        mData.child("SocketServer").child(this.ID).child("dev0").setValue(dev0);
        mData.child("SocketServer").child(this.ID).child("dev1").setValue(dev1);
        mData.child("SocketServer").child(this.ID).child("buzzer").setValue(buzzer);
        mData.child("SocketServer").child(this.ID).child("sim0").setValue(sim0);
        mData.child("SocketServer").child(this.ID).child("sim1").setValue(sim1);
    }

    private void triggerFirebase(){
        mData.child("SocketServer").child(this.ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals("dev0")) {
                        dev0 = Integer.valueOf(dataSnapshot1.getValue().toString());
                    } else if (dataSnapshot1.getKey().equals("dev1")) {
                        dev1 =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    } else if (dataSnapshot1.getKey().equals("buzzer")){
                        buzzer =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    } else if (dataSnapshot1.getKey().equals("sim0")){
                        sim0 =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    } else if (dataSnapshot1.getKey().equals("sim1")){
                        sim1 =  Integer.valueOf(dataSnapshot1.getValue().toString());
                    }
                }
                Log.d(TAG,"Node has already change!");
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

    public boolean isImplemented() {
        return isImplemented;
    }

    public void setImplemented(boolean implemented) {
        isImplemented = implemented;
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
        mData.child("SocketServer").child(this.ID).child("strengthWifi").setValue(strengthWifi);
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
    }

    public int getSim1() {
        return sim1;
    }

    public void setSim1(int sim1) {
        this.sim1 = sim1;
    }
}
