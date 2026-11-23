package com.hkcare.model;

public class DuocSiTruong extends NguoiDung {
    private String maCHQuanLy;

    public boolean themDuocSiTruong() { return true; }
    public boolean capNhatDuocSiTruong() { return true; }
    public boolean xoaDuocSiTruong() { return true; }

    @Override public boolean themNguoiDung() { return themDuocSiTruong(); }
    @Override public boolean capNhatNguoiDung() { return capNhatDuocSiTruong(); }
    @Override public boolean xoaNguoiDung() { return xoaDuocSiTruong(); }

    public String getMaCHQuanLy() { return maCHQuanLy; }
    public void setMaCHQuanLy(String maCHQuanLy) { this.maCHQuanLy = maCHQuanLy; }
}
