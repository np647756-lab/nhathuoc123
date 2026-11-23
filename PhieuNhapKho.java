package com.hkcare.model;

import java.time.LocalDate;
import java.util.List;

public class PhieuNhapKho {
    private String maPN;
    private String maNCC;
    private String maCH;
    private LocalDate ngayNhap;
    private long tongTien;
    private List<ChiTietNhap> chiTiet;

    public PhieuNhapKho() {}

    public String getMaPN() { return maPN; }
    public void setMaPN(String maPN) { this.maPN = maPN; }
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getMaCH() { return maCH; }
    public void setMaCH(String maCH) { this.maCH = maCH; }
    public LocalDate getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(LocalDate ngayNhap) { this.ngayNhap = ngayNhap; }
    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }
    public List<ChiTietNhap> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietNhap> chiTiet) { this.chiTiet = chiTiet; }
}
