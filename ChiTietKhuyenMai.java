package com.hkcare.model;

public class ChiTietKhuyenMai {
    private String maSP;
    private int soLuongMua;
    private int soLuongTang;
    private double phanTramGiam;

    public ChiTietKhuyenMai() {}

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public int getSoLuongMua() { return soLuongMua; }
    public void setSoLuongMua(int soLuongMua) { this.soLuongMua = soLuongMua; }
    public int getSoLuongTang() { return soLuongTang; }
    public void setSoLuongTang(int soLuongTang) { this.soLuongTang = soLuongTang; }
    public double getPhanTramGiam() { return phanTramGiam; }
    public void setPhanTramGiam(double phanTramGiam) { this.phanTramGiam = phanTramGiam; }
}
