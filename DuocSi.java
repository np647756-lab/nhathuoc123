package com.hkcare.model;

public class DuocSi extends NguoiDung {
    private String soChungChi;
    private String ngayCapChungChi;

    public boolean themDuocSi() { return true; }
    public boolean capNhatDuocSi() { return true; }
    public boolean xoaDuocSi() { return true; }

    @Override public boolean themNguoiDung() { return themDuocSi(); }
    @Override public boolean capNhatNguoiDung() { return capNhatDuocSi(); }
    @Override public boolean xoaNguoiDung() { return xoaDuocSi(); }

    public String getSoChungChi() { return soChungChi; }
    public void setSoChungChi(String soChungChi) { this.soChungChi = soChungChi; }
    public String getNgayCapChungChi() { return ngayCapChungChi; }
    public void setNgayCapChungChi(String ngayCapChungChi) { this.ngayCapChungChi = ngayCapChungChi; }
}
