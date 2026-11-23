package com.hkcare.model;

import java.time.LocalDate;
import java.util.List;

public class DonDatHang {
    private String maPO;
    private String maNCC;
    private LocalDate ngayDat;
    private String trangThai;
    private List<ChiTietNhap> chiTiet;

    public DonDatHang() {}

    public String getMaPO() { return maPO; }
    public void setMaPO(String maPO) { this.maPO = maPO; }
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public LocalDate getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDate ngayDat) { this.ngayDat = ngayDat; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public List<ChiTietNhap> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietNhap> chiTiet) { this.chiTiet = chiTiet; }
}
