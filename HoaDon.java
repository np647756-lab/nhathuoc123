package com.hkcare.model;

import java.time.LocalDateTime;
import java.util.List;

public class HoaDon {
    private String maHD;
    private String maNV;
    private String maKH;
    private LocalDateTime ngayLap;
    private long tongTien;
    private String hinhThucThanhToan;
    private String trangThai;
    private List<ChiTietHoaDon> chiTiet;

    public HoaDon() {}

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }
    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }
    public String getHinhThucThanhToan() { return hinhThucThanhToan; }
    public void setHinhThucThanhToan(String hinhThucThanhToan) { this.hinhThucThanhToan = hinhThucThanhToan; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public List<ChiTietHoaDon> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietHoaDon> chiTiet) { this.chiTiet = chiTiet; }
}
