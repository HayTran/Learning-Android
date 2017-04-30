package com.example.vanhay.testsqlite;

/**
 * Created by Van Hay on 4/30/2017.
 */

public class ChiTieu {
    String id;
    public String ten;
    public int chiPhi;
    public String ghiChu;

    public ChiTieu(String id, String ten, int chiPhi, String ghiChu) {
        this.id = id;
        this.ten = ten;
        this.chiPhi = chiPhi;
        this.ghiChu = ghiChu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(int chiPhi) {
        this.chiPhi = chiPhi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
