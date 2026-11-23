package com.hkcare.model;

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String maSoThue;
    private String diaChi;
    private String sdt;
    private String email;
    private String ghiChu;
    private long congNo;

    public NhaCungCap() {}

    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
    public String getMaSoThue() { return maSoThue; }
    public void setMaSoThue(String maSoThue) { this.maSoThue = maSoThue; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public long getCongNo() { return congNo; }
    public void setCongNo(long congNo) { this.congNo = congNo; }
}
