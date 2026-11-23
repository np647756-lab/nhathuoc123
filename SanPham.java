package com.hkcare.model;

public class SanPham {
    private String maSP;
    private String tenThuoc;
    private String donViTinh;
    private long giaBan;
    private int tonKho;
    private String maDM;
    private String maNCC;
    private String hinhAnh;

    public SanPham() {}

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public String getTenThuoc() { return tenThuoc; }
    public void setTenThuoc(String tenThuoc) { this.tenThuoc = tenThuoc; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
    public long getGiaBan() { return giaBan; }
    public void setGiaBan(long giaBan) { this.giaBan = giaBan; }
    public int getTonKho() { return tonKho; }
    public void setTonKho(int tonKho) { this.tonKho = tonKho; }
    public String getMaDM() { return maDM; }
    public void setMaDM(String maDM) { this.maDM = maDM; }
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}
