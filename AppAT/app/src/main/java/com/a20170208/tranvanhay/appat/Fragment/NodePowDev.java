package com.a20170208.tranvanhay.appat.Fragment;

/**
 * Created by Van Hay on 01-Jun-17.
 */

public class NodePowDev {
    private String MACAddr; // help server recognize
    private String ID;      // help user recognize
    private int strengthWifi;
    private boolean dev0, dev1, buzzer, sim0, sim1;
    private String timeOperation;
    private boolean isImplemented;


    public NodePowDev() {
    }

    public NodePowDev(String MACAddr, String ID, int strengthWifi, boolean dev0, boolean dev1, boolean buzzer, boolean sim0, boolean sim1, String timeOperation, boolean isImplemented) {
        this.MACAddr = MACAddr;
        this.ID = ID;
        this.strengthWifi = strengthWifi;
        this.dev0 = dev0;
        this.dev1 = dev1;
        this.buzzer = buzzer;
        this.sim0 = sim0;
        this.sim1 = sim1;
        this.timeOperation = timeOperation;
        this.isImplemented = isImplemented;
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

    public int getStrengthWifi() {
        return strengthWifi;
    }

    public void setStrengthWifi(int strengthWifi) {
        this.strengthWifi = strengthWifi;
    }

    public boolean isDev0() {
        return dev0;
    }

    public void setDev0(boolean dev0) {
        this.dev0 = dev0;
    }

    public boolean isDev1() {
        return dev1;
    }

    public void setDev1(boolean dev1) {
        this.dev1 = dev1;
    }

    public boolean isBuzzer() {
        return buzzer;
    }

    public void setBuzzer(boolean buzzer) {
        this.buzzer = buzzer;
    }

    public boolean isSim0() {
        return sim0;
    }

    public void setSim0(boolean sim0) {
        this.sim0 = sim0;
    }

    public boolean isSim1() {
        return sim1;
    }

    public void setSim1(boolean sim1) {
        this.sim1 = sim1;
    }

    public String getTimeOperation() {
        return timeOperation;
    }

    public void setTimeOperation(String timeOperation) {
        this.timeOperation = timeOperation;
    }

    public boolean isImplemented() {
        return isImplemented;
    }

    public void setImplemented(boolean implemented) {
        isImplemented = implemented;
    }

    @Override
    public String toString() {
        return "NodePowDev{" +
                "MACAddr='" + MACAddr + '\'' +
                ", ID='" + ID + '\'' +
                ", strengthWifi=" + strengthWifi +
                ", dev0=" + dev0 +
                ", dev1=" + dev1 +
                ", buzzer=" + buzzer +
                ", sim0=" + sim0 +
                ", sim1=" + sim1 +
                ", isImplemented=" + isImplemented +
                '}';
    }
}
