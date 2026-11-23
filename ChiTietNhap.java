package com.hkcare.model;

public class ChiTietNhap {
    private String maSP;
    private int soLuong;
    private long donGiaNhap;
    private long thanhTien;

    public ChiTietNhap() {}

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public long getDonGiaNhap() { return donGiaNhap; }
    public void setDonGiaNhap(long donGiaNhap) { this.donGiaNhap = donGiaNhap; }
    public long getThanhTien() { return thanhTien; }
    public void setThanhTien(long thanhTien) { this.thanhTien = thanhTien; }
}
