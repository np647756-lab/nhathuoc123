package com.hkcare.model;

import java.time.LocalDate;

public class QuanTriVien extends NguoiDung {
    private LocalDate ngayBatDau;
    private String ghiChuQuyen;

    public boolean themQuanTriVien() { return true; }
    public boolean capNhatQuyen() { return true; }
    public boolean xoaQuanTriVien() { return true; }

    @Override public boolean themNguoiDung() { return themQuanTriVien(); }
    @Override public boolean capNhatNguoiDung() { return capNhatQuyen(); }
    @Override public boolean xoaNguoiDung() { return xoaQuanTriVien(); }

    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public String getGhiChuQuyen() { return ghiChuQuyen; }
    public void setGhiChuQuyen(String ghiChuQuyen) { this.ghiChuQuyen = ghiChuQuyen; }
}
