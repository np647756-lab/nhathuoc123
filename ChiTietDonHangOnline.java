package com.hkcare.model;

public class ChiTietDonHangOnline {
    private String maSP;
    private int soLuong;
    private long donGia;
    private long thanhTien;

    public ChiTietDonHangOnline() {}

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public long getDonGia() { return donGia; }
    public void setDonGia(long donGia) { this.donGia = donGia; }
    public long getThanhTien() { return thanhTien; }
    public void setThanhTien(long thanhTien) { this.thanhTien = thanhTien; }
}
