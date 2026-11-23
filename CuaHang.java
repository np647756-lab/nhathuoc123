package com.hkcare.model;

public class CuaHang {
    private String maCH;
    private String tenCH;
    private String diaChi;
    private String sdt;

    public CuaHang() {}
    public CuaHang(String maCH, String tenCH, String diaChi, String sdt) {
        this.maCH = maCH;
        this.tenCH = tenCH;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    public String getMaCH() { return maCH; }
    public void setMaCH(String maCH) { this.maCH = maCH; }
    public String getTenCH() { return tenCH; }
    public void setTenCH(String tenCH) { this.tenCH = tenCH; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}
