package com.hkcare.model;

import java.time.LocalDateTime;
import java.util.List;

public class DonHangOnline {
    private String maDon;
    private String maKH;
    private String maND;
    private String diaChiGiao;
    private String paymentId;
    private long thanhTien;
    private String trangThai;
    private LocalDateTime ngayDat;
    private List<ChiTietDonHangOnline> chiTiet;

    public DonHangOnline() {}

    public String getMaDon() { return maDon; }
    public void setMaDon(String maDon) { this.maDon = maDon; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getMaND() { return maND; }
    public void setMaND(String maND) { this.maND = maND; }
    public String getDiaChiGiao() { return diaChiGiao; }
    public void setDiaChiGiao(String diaChiGiao) { this.diaChiGiao = diaChiGiao; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public long getThanhTien() { return thanhTien; }
    public void setThanhTien(long thanhTien) { this.thanhTien = thanhTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }
    public List<ChiTietDonHangOnline> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietDonHangOnline> chiTiet) { this.chiTiet = chiTiet; }
}
